package org.jasongatica.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.jasongatica.bean.Cargo;
import org.jasongatica.db.Conexion;
import org.jasongatica.report.GenerarReporte;
import org.jasongatica.sistema.Principal;

public class CargoController implements Initializable{
    private Principal escenarioPrincipal;
    private ObservableList<Cargo> listarCargo;
    private enum operaciones{Nuevo,Guardar,Editar,Actualizar,Eliminar,Cancelar,Ninguno} 
    private operaciones tipoDeOperaciones = operaciones.Ninguno;
    @FXML private ComboBox cmbCodigoCargo;
    @FXML private TextField txtNombreCargo;
    @FXML private Button btnAgregar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblCargo;
    @FXML private TableColumn colCodigoCargo;
    @FXML private TableColumn colNombreCargo; 
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public ObservableList<Cargo> getCargo(){
        ArrayList lista = new ArrayList<Cargo>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarCargos}");
            ResultSet resultado =procedimiento.executeQuery();
            
            while(resultado.next()){
               lista.add(new Cargo(resultado.getInt("codigoCargo"),
               resultado.getString("nombreCargo")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listarCargo = FXCollections.observableList(lista);
    }
   
    
     private Cargo buscarCargo(int codigoCargo){
        Cargo resultado = null;
            try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarCargos (?)}");
            procedimiento.setInt(1,codigoCargo);
            ResultSet registro = procedimiento.executeQuery();
            
                while(registro.next()){
                resultado = new Cargo(registro.getInt("codigoCargo"),
                registro.getString("nombreCargo"));
            
                }
            }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        
        }
        return resultado;
    }   
    
    private void cargarDatos(){
        tblCargo.setItems(getCargo());
        colCodigoCargo.setCellValueFactory(new PropertyValueFactory<Cargo,Integer>("codigoCargo"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo,String>("nombreCargo"));
    }
       
    public void seleccionar(){
        Cargo cargoSeleccionado = (Cargo)tblCargo.getSelectionModel().getSelectedItem();
        cmbCodigoCargo.getSelectionModel().select(cargoSeleccionado.toString());
        txtNombreCargo.setText(cargoSeleccionado.getNombreCargo());
    }
       
    private void ingresar() {
        Cargo registro = new Cargo();
        registro.setNombreCargo(txtNombreCargo.getText());
        try{
          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarCargos (?)}"); 
          procedimiento.setString(1,registro.getNombreCargo());
          procedimiento.execute();
          listarCargo.add(registro);
        }catch(SQLException e){
           e.printStackTrace();   
        }
    }
    
    private void limpiar() {
       txtNombreCargo.setText("");
    }        
    
    private void activar() {
       txtNombreCargo.setDisable(false);
        
       cmbCodigoCargo.setEditable(true);
       txtNombreCargo.setEditable(true);        
    }    
    
    
    private void desactivar() {
        cmbCodigoCargo.setDisable(true);
        txtNombreCargo.setDisable(true);
        btnAgregar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnReporte.setDisable(false);

        
        cmbCodigoCargo.setEditable(false);
        txtNombreCargo.setEditable(false);
    }      
   
    public void cancelar(){
        btnAgregar.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnReporte.setDisable(false);
        btnModificar.setDisable(false);
    }
    
    public void nuevo(){
        switch(tipoDeOperaciones){
            case  Ninguno:
                cmbCodigoCargo.setDisable(true);
                tipoDeOperaciones = operaciones.Guardar;
                activar();
                limpiar();
                btnAgregar.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnModificar.setDisable(true);
                btnReporte.setDisable(true);
            break;
            case Guardar:
                ingresar();
                desactivar();
                btnAgregar.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnModificar.setDisable(false);
                btnReporte.setDisable(false);
                tipoDeOperaciones = operaciones.Ninguno;
                cargarDatos();
            break;
        }
    }
    
    
    private void actualizar() throws SQLException {
       PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarCargos (?,?)}");
       Cargo registro= (Cargo) tblCargo.getSelectionModel().getSelectedItem();
        
       registro.setCodigoCargo(registro.getCodigoCargo());
       registro.setNombreCargo(txtNombreCargo.getText());
        
       procedimiento.setInt(1,registro.getCodigoCargo());
       procedimiento.setString(2,registro.getNombreCargo());
       procedimiento.execute();
       listarCargo.add(registro); 
    }
    
    
    
    public void editar() throws SQLException{
        switch(tipoDeOperaciones){
            case Ninguno:
                if (tblCargo.getSelectionModel().getSelectedItem() != null){
                    cmbCodigoCargo.setDisable(false);
                    btnModificar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    tipoDeOperaciones = operaciones.Actualizar;
                    btnAgregar.setDisable(true);
                    btnEliminar.setDisable(true);
                    activar();
                }
            break;
            
            case Actualizar:
                actualizar();
                btnModificar.setText("Editar");
                btnReporte.setText("Reportar");
                btnAgregar.setDisable(false);
                btnEliminar.setDisable(false);
                desactivar();
                cargarDatos();
                tipoDeOperaciones = operaciones.Ninguno;       
            break;
        }
    }
    
    
    public void eliminar(){
        if(tipoDeOperaciones == operaciones.Guardar){
            cancelar();
            desactivar();
            tipoDeOperaciones = operaciones.Ninguno;
        }else{
            tipoDeOperaciones = operaciones.Eliminar;
            if(tblCargo.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null,"Seguro que desea eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                 try{
                     PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarCargos (?)}");
                     procedimiento.setInt(1,((Cargo)tblCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
                     procedimiento.execute();
                     listarCargo.remove(tblCargo.getSelectionModel().getSelectedIndex());
                     limpiar();
                     cargarDatos();
                     tipoDeOperaciones = operaciones.Ninguno;
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null,"Debe Seleccionar un registro a eliminar");
            }           
        } 
    } 
   
    public void imprimirReporte(){
        if(tipoDeOperaciones == operaciones.Actualizar){
            cancelar();
            desactivar();
            btnReporte.setText("Reporte");
            tipoDeOperaciones = operaciones.Ninguno;
            }else{
                if(tblCargo.getSelectionModel().getSelectedItem() != null){
                    int codigoCargo = (((Cargo)tblCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
                    Map parametros = new HashMap();
                    parametros.put("p_codigoCargo", codigoCargo);
                    GenerarReporte.mostrarReporte("ReporteCargo.jasper","Reporte Cargo",parametros);      
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro a imprimir");
            }       
        }
    }    
    
    public void imprimirReporteGeneral(){
            int codigoCargo = 0;
            Map parametros = new HashMap();
            parametros.put("codigoCargo", codigoCargo);
            GenerarReporte.mostrarReporte("ReporteGeneralCargo.jasper","Reporte Cargo",parametros);      
    }      
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
