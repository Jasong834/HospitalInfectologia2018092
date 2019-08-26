
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
import org.jasongatica.bean.ContactoUrgencia;
import org.jasongatica.bean.Pacientes;
import org.jasongatica.db.Conexion;
import org.jasongatica.report.GenerarReporte;
import org.jasongatica.sistema.Principal;


public class ContactoUrgenciaController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{Nuevo,Guardar,Editar,Actualizar,Eliminar,Cancelar,Ninguno} 
    private operaciones tipoDeOperaciones = operaciones.Ninguno;
    private ObservableList<Pacientes> listarPacientes;
    private ObservableList<ContactoUrgencia> listarContactos;    
    
    @FXML private ComboBox cmbCodigoContacto;
    @FXML private ComboBox cmbPaciente;
    @FXML private TextField txtNombre;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtApellido;
    @FXML private Button btnAgregar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblContacto;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colApellido;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colPaciente;       
   
    @Override 
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbPaciente.setItems(getPacientes());
        cmbCodigoContacto.setItems(getContactoUrgencia());
    }

    public ObservableList <Pacientes> getPacientes() {
        ArrayList<Pacientes> lista = new ArrayList<Pacientes>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarPacientes}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Pacientes(resultado.getInt("codigoPaciente"),
                resultado.getString("DPI"),
                resultado.getString("nombres"),
                resultado.getString("apellidos"),
                resultado.getString("fechaNacimiento"),
                resultado.getInt("edad"),
                resultado.getString("direccion"),
                resultado.getString("ocupacion"),
                resultado.getString("sexo")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
      return listarPacientes = FXCollections.observableList(lista);
    }     
    
    
    public Pacientes buscarPacientes(int codigoPaciente){
        Pacientes resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarPacientes (?)}");
            procedimiento.setInt(1, codigoPaciente);
            ResultSet registro = procedimiento.executeQuery();
            
           while(registro.next()){
                resultado = new Pacientes(registro.getInt("codigoPaciente"),
                registro.getString("DPI"),
                registro.getString("nombres"),
                registro.getString("apellidos"),
                registro.getString("fechaNacimiento"),
                registro.getInt("edad"),
                registro.getString("direccion"),
                registro.getString("ocupacion"),
                registro.getString("sexo"));
            
            }
        }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        
        }
        return resultado;
    }
        
    
    
    public ObservableList <ContactoUrgencia> getContactoUrgencia() {
        ArrayList<ContactoUrgencia> lista = new ArrayList<ContactoUrgencia>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarContactoUrgencian}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new ContactoUrgencia(resultado.getInt("codigoContactoUrgencia"),
                resultado.getString("nombre"),
                resultado.getString("apellido"),
                resultado.getString("numeroContacto"),
                resultado.getInt("codigoPaciente")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
      return listarContactos = FXCollections.observableList(lista);
    }     
    
    public ContactoUrgencia buscarContacto(int codigoContactoUrgencia){
            ContactoUrgencia resultado = null;
            try{
                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarContactoUrgengian (?)}");
                procedimiento.setInt(1,codigoContactoUrgencia);
                ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
                resultado = new ContactoUrgencia(registro.getInt("codigoContactoUrgencia"),
                registro.getString("nombre"),
                registro.getString("apellido"),
                registro.getString("numeroContacto"),
                registro.getInt("codigoPaciente"));
            
                }
            }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        
        }
        return resultado;
    }    
    
    
    public void cargarDatos(){
        
        tblContacto.setItems(getContactoUrgencia());
        colCodigo.setCellValueFactory(new PropertyValueFactory <ContactoUrgencia, Integer>("codigoContactoUrgencia"));
        colNombre.setCellValueFactory(new PropertyValueFactory <ContactoUrgencia,String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory <ContactoUrgencia, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory <ContactoUrgencia, Integer>("numeroContacto"));  
        colPaciente.setCellValueFactory(new PropertyValueFactory <ContactoUrgencia, Integer>("codigoPaciente"));   
    }    
    
   
    public void seleccionar(){
        ContactoUrgencia contactoSeleccionado = (ContactoUrgencia) tblContacto.getSelectionModel().getSelectedItem();
        cmbCodigoContacto.getSelectionModel().select(contactoSeleccionado.toString());
        txtNombre.setText(contactoSeleccionado.getNombre());
        txtApellido.setText(contactoSeleccionado.getApellido());
        txtTelefono.setText(contactoSeleccionado.getNumeroContacto());
        
        int codigoPaciente = contactoSeleccionado.getCodigoPaciente();
        Pacientes pacienteSeleccionado = buscarPacientes(codigoPaciente);
        
        cmbPaciente.getSelectionModel().select(pacienteSeleccionado.getCodigoPaciente() + " - " + pacienteSeleccionado.getNombres() + " " + pacienteSeleccionado.getApellidos());
    }    
    
    
    
    private void ingresar() {
       ContactoUrgencia registro = new ContactoUrgencia();
       registro.setNombre(txtNombre.getText());
       registro.setApellido(txtApellido.getText());
       registro.setNumeroContacto(txtTelefono.getText());
       String codigoPaciente,codigoPaciente1;
        
       Integer posicion;
       codigoPaciente = cmbPaciente.getValue().toString();
       posicion = codigoPaciente.indexOf("-");
       codigoPaciente1 = codigoPaciente.substring(0,posicion);
       registro.setCodigoContactoUrgencia(Integer.parseInt(codigoPaciente1));

       
       try{
          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarContactoUrgencia (?,?,?,?)}"); 
          procedimiento.setString(1,registro.getNombre());
          procedimiento.setString(2,registro.getApellido());
          procedimiento.setString(3,registro.getNumeroContacto());
          procedimiento.setInt(4,Integer.parseInt(codigoPaciente1));
          procedimiento.execute();
          listarContactos.add(registro);
       }catch(SQLException e){
       
           e.printStackTrace(); 
       }  
    }      
    
    private void limpiar() {
       txtNombre.setText("");
       txtApellido.setText("");
       txtTelefono.setText("");
    }
    
    private void activar() {
        txtNombre.setDisable(false);
        txtApellido.setDisable(false);
        txtTelefono.setDisable(false);
        cmbPaciente.setDisable(false);
        
        cmbCodigoContacto.setEditable(true);
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtTelefono.setEditable(true);
        cmbPaciente.setEditable(true);
    } 
    
    private void desactivar() {
        cmbCodigoContacto.setDisable(true);
        txtNombre.setDisable(true);
        txtApellido.setDisable(true);
        txtTelefono.setDisable(true);
        cmbPaciente.setDisable(true);
        btnAgregar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnReporte.setDisable(false);
        
        cmbCodigoContacto.setEditable(false);
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtTelefono.setEditable(false);
        cmbPaciente.setEditable(false);
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
                cmbCodigoContacto.setDisable(true);
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
        PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call  sp_ModificarContactoUrgencia (?,?,?,?,?)}");
        ContactoUrgencia registro= (ContactoUrgencia) tblContacto.getSelectionModel().getSelectedItem();
                
        registro.setCodigoContactoUrgencia(registro.getCodigoContactoUrgencia());
        registro.setNombre(txtNombre.getText());
        registro.setApellido(txtApellido.getText());
        registro.setNumeroContacto(txtTelefono.getText());
        registro.setCodigoPaciente(registro.getCodigoPaciente());
      
        procedimiento.setInt(1,registro.getCodigoContactoUrgencia());
        procedimiento.setString(2,registro.getNombre());
        procedimiento.setString(3,registro.getApellido());
        procedimiento.setString(4,registro.getNumeroContacto());
        procedimiento.setInt(5,registro.getCodigoPaciente());
        procedimiento.execute();
        listarContactos.add(registro);
       }   
    
    public void editar() throws SQLException{
        switch(tipoDeOperaciones){
            case Ninguno:
                if (tblContacto.getSelectionModel().getSelectedItem() != null){
                    cmbCodigoContacto.setDisable(false);
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
            break ;
        }
    }    
    
    public void eliminar(){
        if(tipoDeOperaciones == operaciones.Guardar){
            cancelar();
            desactivar();
            tipoDeOperaciones = operaciones.Ninguno;
           
        }else{
            tipoDeOperaciones = operaciones.Eliminar;
            if(tblContacto.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null,"Seguro que desea eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                    try{
                     PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarContactoUrgencia (?)}");
                     procedimiento.setInt(1,((ContactoUrgencia)tblContacto.getSelectionModel().getSelectedItem()).getCodigoContactoUrgencia());
                     procedimiento.execute();
                     listarContactos.remove(tblContacto.getSelectionModel().getSelectedIndex());
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
        if(tblContacto.getSelectionModel().getSelectedItem() != null){
            int codigoContactoUrgencia = (((ContactoUrgencia)tblContacto.getSelectionModel().getSelectedItem()).getCodigoContactoUrgencia());
            Map parametros = new HashMap();
            parametros.put("p_codigoContactoUrgencia",codigoContactoUrgencia);
            GenerarReporte.mostrarReporte("ReporteContacto.jasper","Reporte Contacto",parametros);      
        }else{
            JOptionPane.showMessageDialog(null,"Debe seleccionar un registro a imprimir");
        }
    }    
    
    public void imprimirReporteGeneral(){
            int codigoContactoUrgencia = 0;
            Map parametros = new HashMap();
            parametros.put("codigoContactoUrgencia", codigoContactoUrgencia);
            GenerarReporte.mostrarReporte("ReporteGeneralContacto.jasper","Reporte Contacto",parametros);      
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
  
    public void ventanaPaciente(){
        escenarioPrincipal.ventanaPacientes();
    }    
    
}
