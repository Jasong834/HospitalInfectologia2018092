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
import org.jasongatica.bean.Especialidad;
import org.jasongatica.db.Conexion;
import org.jasongatica.report.GenerarReporte;
import org.jasongatica.sistema.Principal;


public class EspecialidadController implements Initializable{
    private Principal escenarioPrincipal;
    private ObservableList<Especialidad> listarEspecialidad;
    private enum operaciones{Nuevo,Guardar,Editar,Actualizar,Eliminar,Cancelar,Ninguno} 
    private operaciones tipoDeOperaciones = operaciones.Ninguno;   
    
    @FXML private ComboBox cmbCodigoEspecialidad;
    @FXML private TextField txtNombreEspecialidad;
    @FXML private Button btnAgregar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblEspecialidad;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colNombre;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoEspecialidad.setItems(getEspecialidad());
    }

    public ObservableList<Especialidad> getEspecialidad(){
        ArrayList lista = new ArrayList<Especialidad>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarEspecialidades}");
            ResultSet resultado =procedimiento.executeQuery();
            
            while(resultado.next()){
               lista.add(new Especialidad(resultado.getInt("codigoEspecialidad"),
               resultado.getString("nombreEspecialidad")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listarEspecialidad = FXCollections.observableList(lista);
    }
    
    private Especialidad buscarEspecialidad(int codigoEspecialidad){
        Especialidad resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarEspecialidad (?)}");
            procedimiento.setInt(1,codigoEspecialidad);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
                resultado = new Especialidad(registro.getInt("codigoEspecialidad"),
                registro.getString("nombreEspecialidad"));
            
            }
       }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        }
        return resultado;
    }
    
    
    public void seleccionar(){
        Especialidad especialidadSeleccionada = (Especialidad)tblEspecialidad.getSelectionModel().getSelectedItem();
        cmbCodigoEspecialidad.getSelectionModel().select(especialidadSeleccionada.toString());
        txtNombreEspecialidad.setText(especialidadSeleccionada.getNombreEspecialidad());
    }    
    
    
    private void cargarDatos(){
        tblEspecialidad.setItems(getEspecialidad());
        colCodigo.setCellValueFactory(new PropertyValueFactory<Especialidad,Integer>("codigoEspecialidad"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Especialidad,String>("nombreEspecialidad"));
    } 
    
    private void ingresar() {
       Especialidad registro = new Especialidad();
       registro.setNombreEspecialidad(txtNombreEspecialidad.getText());
       try{
          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregrarEspecialidad (?)}"); 
          procedimiento.setString(1,registro.getNombreEspecialidad());
          procedimiento.execute();
          listarEspecialidad.add(registro);
       }catch(SQLException e){
           e.printStackTrace();   
       }
    }
    
    private void limpiar() {
       txtNombreEspecialidad.setText("");
    }        
    
    private void activar() {
       txtNombreEspecialidad.setDisable(false);
        
       cmbCodigoEspecialidad.setEditable(true);
       txtNombreEspecialidad.setEditable(true);        
    }    
    
    
    private void desactivar() {
        cmbCodigoEspecialidad.setDisable(true);
        txtNombreEspecialidad.setDisable(true);
        btnAgregar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnReporte.setDisable(false);
        
        cmbCodigoEspecialidad.setEditable(false);
        txtNombreEspecialidad.setEditable(false);
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
                cmbCodigoEspecialidad.setDisable(true);
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
       PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarEspecialidad (?,?)}");
       Especialidad registro= (Especialidad) tblEspecialidad.getSelectionModel().getSelectedItem();
        
       registro.setCodigoEspecialidad(registro.getCodigoEspecialidad());
       registro.setNombreEspecialidad((txtNombreEspecialidad.getText()));
        
       procedimiento.setInt(1,registro.getCodigoEspecialidad());
       procedimiento.setString(2,registro.getNombreEspecialidad());
       procedimiento.execute();
       listarEspecialidad.add(registro); 
    }  
    
    public void editar() throws SQLException{
        switch(tipoDeOperaciones){
            case Ninguno:
                if (tblEspecialidad.getSelectionModel().getSelectedItem() != null){
                    cmbCodigoEspecialidad.setDisable(false);
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
            if(tblEspecialidad.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null,"Seguro que desea eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                 try{
                     PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarEspecialidad (?)}");
                     procedimiento.setInt(1,((Especialidad)tblEspecialidad.getSelectionModel().getSelectedItem()).getCodigoEspecialidad());
                     procedimiento.execute();
                     listarEspecialidad.remove(tblEspecialidad.getSelectionModel().getSelectedIndex());
                     limpiar();
                     cargarDatos();
                     tipoDeOperaciones = operaciones.Ninguno;
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
            }
            else{
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
                if(tblEspecialidad.getSelectionModel().getSelectedItem() != null){
                    int codigoEspecialidad = (((Especialidad)tblEspecialidad.getSelectionModel().getSelectedItem()).getCodigoEspecialidad());
                    Map parametros = new HashMap();
                    parametros.put("p_codigoEspecialidad",codigoEspecialidad);
                    GenerarReporte.mostrarReporte("ReporteEspecialidad.jasper","Reporte Especialidad",parametros);      
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro a imprimir");
            }
        }
    } 
    
    
    public void imprimirReporteGeneral(){
            int codigoEspecialidad = 0;
            Map parametros = new HashMap();
            parametros.put("codigoEspecialidad", codigoEspecialidad);
            GenerarReporte.mostrarReporte("ReporteGeneralEspecialidad.jasper","Reporte Especialidad",parametros);      
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
