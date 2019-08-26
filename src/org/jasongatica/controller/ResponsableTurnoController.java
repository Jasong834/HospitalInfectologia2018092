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
import org.jasongatica.bean.Areas;
import org.jasongatica.bean.Cargo;
import org.jasongatica.bean.ResponsableTurno;
import org.jasongatica.db.Conexion;
import org.jasongatica.report.GenerarReporte;
import org.jasongatica.sistema.Principal;


public class ResponsableTurnoController implements  Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{Nuevo,Guardar,Editar,Actualizar,Eliminar,Cancelar,Ninguno} 
    private operaciones tipoDeOperaciones = operaciones.Ninguno;  
    private ObservableList<ResponsableTurno> listarResponsable;
    private ObservableList<Cargo> listarCargo;
    private ObservableList<Areas> listarAreas;
    
    @FXML private ComboBox cmbCodigo;
    @FXML private ComboBox cmbArea;
    @FXML private ComboBox cmbCargo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtTelefono;
    @FXML private TableView tblResponsable;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colApellido;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colArea;
    @FXML private TableColumn colCargo;
    @FXML private Button btnAgregar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblArea;    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigo.setItems(getResponsable());
        cmbArea.setItems(getAreas());
        cmbCargo.setItems(getCargo());
    }

    public ObservableList<Areas> getAreas(){
        ArrayList lista = new ArrayList<Areas>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarAreas}");
            ResultSet resultado =procedimiento.executeQuery();
            
            while(resultado.next()){
               lista.add(new Areas(resultado.getInt("codigoArea"),
               resultado.getString("nombreArea")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listarAreas = FXCollections.observableList(lista);
    }


    private Areas buscarArea(int codigoArea){
        Areas resultado = null;
            try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarAreas (?)}");
            procedimiento.setInt(1,codigoArea);
            ResultSet registro = procedimiento.executeQuery();
            
                while(registro.next()){
                resultado = new Areas(registro.getInt("codigoArea"),
                registro.getString("nombreArea"));
            
                }
            }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        
        }
        return resultado;
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
    
    public ObservableList<ResponsableTurno> getResponsable(){
        ArrayList lista = new ArrayList<ResponsableTurno>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarResponsableTurno}");
            ResultSet resultado =procedimiento.executeQuery();
            
            while(resultado.next()){
               lista.add(new ResponsableTurno(resultado.getInt("codigoResponsableTurno"),
               resultado.getString("nombreResponsable"),
               resultado.getString("apellidoResponsable"),
               resultado.getString("telefonResponsable"),
               resultado.getInt("codigoArea"),
               resultado.getInt("codigoCargo")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listarResponsable = FXCollections.observableList(lista);
    }    
    
    
    private ResponsableTurno buscarResponsable(int codigoResponsableTurno){
        ResponsableTurno resultado = null;
            try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarResponsableTurno (?)}");
            procedimiento.setInt(1,codigoResponsableTurno);
            ResultSet registro = procedimiento.executeQuery();
            
                while(registro.next()){
                resultado = new ResponsableTurno(registro.getInt("codigoResponsableTurno"),
                registro.getString("nombreResponsable"),
                registro.getString("apellidoResponsable"),
                registro.getString("telefonResponsable"),
                registro.getInt("codigoArea"),
                registro.getInt("codigoCargo")                );
                }
            }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        
        }
        return resultado;
    }     
        
    public void cargarDatos(){
        tblResponsable.setItems(getResponsable());
        colCodigo.setCellValueFactory(new PropertyValueFactory <ResponsableTurno, Integer>("codigoResponsableTurno"));
        colNombre.setCellValueFactory(new PropertyValueFactory <ResponsableTurno,String>("nombreResponsable"));
        colApellido.setCellValueFactory(new PropertyValueFactory <ResponsableTurno,String>("apellidoResponsable"));
        colTelefono.setCellValueFactory(new PropertyValueFactory <ResponsableTurno,String>("telefonResponsable"));   
        colArea.setCellValueFactory(new PropertyValueFactory <ResponsableTurno, Integer>("codigoArea"));
        colCargo.setCellValueFactory(new PropertyValueFactory <ResponsableTurno, Integer>("codigoCargo"));
    } 

    public void seleccionar(){
        ResponsableTurno responsableSeleccionado = (ResponsableTurno) tblResponsable.getSelectionModel().getSelectedItem();
        cmbCodigo.getSelectionModel().select(responsableSeleccionado.toString());
        txtNombre.setText(responsableSeleccionado.getNombreResponsable());
        txtApellido.setText(responsableSeleccionado.getApellidoResponsable());
        txtTelefono.setText(responsableSeleccionado.getTelefonResponsable());
        
        int codigoArea = responsableSeleccionado.getCodigoArea();
        Areas areaSeleccionado = buscarArea(codigoArea);
        cmbArea.getSelectionModel().select(areaSeleccionado.getCodigoArea() + " - " + areaSeleccionado.getNombreArea());
    
        int codigoCargo= responsableSeleccionado.getCodigoCargo();
        Cargo cargoSeleccionado = buscarCargo(codigoCargo);
        cmbCargo.getSelectionModel().select(cargoSeleccionado.getCodigoCargo() + " - " + cargoSeleccionado.getNombreCargo());
    }
    
    private void ingresar() {
       ResponsableTurno registro = new ResponsableTurno();
       registro.setNombreResponsable(txtNombre.getText());
       registro.setApellidoResponsable(txtApellido.getText());
       registro.setTelefonResponsable(txtTelefono.getText());
       
       
       String codigoArea,codigoArea1,codigoCargo,codigoCargo1;
        
       Integer posicion;
       codigoArea = cmbArea.getValue().toString();
       posicion = codigoArea.indexOf("-");
       codigoArea1 = codigoArea.substring(0,posicion);
       registro.setCodigoArea(Integer.parseInt(codigoArea1));
       
       Integer posicion2;
       codigoCargo = cmbCargo.getValue().toString();
       posicion2 = codigoCargo.indexOf("-");
       codigoCargo1 = codigoCargo.substring(0,posicion);
       registro.setCodigoCargo(Integer.parseInt(codigoCargo1));
       
    

       
       try{
          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarResponsableTurno (?,?,?,?,?)}"); 
          procedimiento.setString(1,registro.getNombreResponsable());
          procedimiento.setString(2,registro.getApellidoResponsable());
          procedimiento.setString(3, registro.getTelefonResponsable());
          procedimiento.setInt(4,Integer.parseInt(codigoArea1));
          procedimiento.setInt(5,Integer.parseInt(codigoCargo1));
   
          procedimiento.execute();
          listarResponsable.add(registro);
       }catch(SQLException e){
       
           e.printStackTrace(); 
       }  
    }     

    private void limpiar() {
       txtTelefono.setText("");
       txtNombre.setText("");
       txtApellido.setText("");
    }    
        
        
    private void activar() {
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtTelefono.setDisable(false);
        cmbArea.setDisable(false);
        cmbCargo.setDisable(false);

        cmbCodigo.setEditable(true);
        cmbArea.setEditable(true);
        cmbCargo.setEditable(true);
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtTelefono.setEditable(true);
    }
        
        
        
        
    private void desactivar() {
        cmbCodigo.setDisable(true);
        cmbArea.setDisable(true);
        cmbCargo.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtTelefono.setDisable(true);
        btnAgregar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnReporte.setDisable(false);        
        
        cmbCodigo.setEditable(false);
        cmbArea.setEditable(false);
        cmbCargo.setEditable(false);
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtTelefono.setEditable(false);
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
                cmbCodigo.setDisable(true);
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
        PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call  sp_ModificarResponsableTurno (?,?,?,?,?,?)}");
        ResponsableTurno registro= (ResponsableTurno) tblResponsable.getSelectionModel().getSelectedItem();
                
        registro.setCodigoResponsableTurno(registro.getCodigoResponsableTurno());
        registro.setNombreResponsable(registro.getNombreResponsable());
        registro.setApellidoResponsable(registro.getApellidoResponsable());
        registro.setTelefonResponsable(registro.getTelefonResponsable());
        registro.setCodigoArea(registro.getCodigoArea());
        registro.setCodigoCargo(registro.getCodigoCargo());
      
        procedimiento.setInt(1,registro.getCodigoResponsableTurno());
        procedimiento.setString(2,registro.getNombreResponsable());
        procedimiento.setString(3,registro.getApellidoResponsable());
        procedimiento.setString(4,registro.getTelefonResponsable());
        procedimiento.setInt(5,registro.getCodigoArea());
        procedimiento.setInt(6,registro.getCodigoCargo());
        procedimiento.execute();
        listarResponsable.add(registro);
    }


    public void editar() throws SQLException{
        switch(tipoDeOperaciones){
            case Ninguno:
                if (tblResponsable.getSelectionModel().getSelectedItem() != null){
                    cmbCodigo.setDisable(false);
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
            if(tblResponsable.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null,"Seguro que desea eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                 try{
                     PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarResponsableTurno (?)}");
                     procedimiento.setInt(1,((ResponsableTurno)tblResponsable.getSelectionModel().getSelectedItem()).getCodigoResponsableTurno());
                     procedimiento.execute();
                     listarResponsable.remove(tblResponsable.getSelectionModel().getSelectedIndex());
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
                if(tblResponsable.getSelectionModel().getSelectedItem() != null){
                    int codigoResponsableTurno = (((ResponsableTurno)tblResponsable.getSelectionModel().getSelectedItem()).getCodigoResponsableTurno());
                    Map parametros = new HashMap();
                    parametros.put("p_codigoResponsableTurno", codigoResponsableTurno);
                    GenerarReporte.mostrarReporte("ReporteResponsable.jasper","Reporte Responsable del Turno",parametros);      
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro a imprimir");
            }
        }        
    }  
    
    public void imprimirReporteGeneral(){
            int codigoResponsableTurno = 0;
            Map parametros = new HashMap();
            parametros.put("codigoResponsableTurno", codigoResponsableTurno);
            GenerarReporte.mostrarReporte("ReporteGeneralResponsable.jasper","Reporte Responsable del Turno",parametros);      
    }      
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaArea(){
        escenarioPrincipal.ventanaArea();
    }
    
    public void ventanaCargo(){
        escenarioPrincipal.ventanaCargo();
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}
