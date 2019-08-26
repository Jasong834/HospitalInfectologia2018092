

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
import org.jasongatica.bean.Pacientes;
import org.jasongatica.db.Conexion;
import org.jasongatica.report.GenerarReporte;
import org.jasongatica.sistema.Principal;


public class PacientesController implements Initializable{
    private enum operaciones{Nuevo,Guardar,Editar,Actualizar,Eliminar,Cancelar,Ninguno} 
    private operaciones tipoDeOperaciones = operaciones.Ninguno;
    private Principal escenarioPrincipal;
    private ObservableList<Pacientes> listarPacientes;
    
    @FXML private ComboBox cmbCodigoPacientes;
    @FXML private TextField txtDPI;
    @FXML private TextField txtNombres;
    @FXML private TextField txtApellidos;
    @FXML private TextField txtNacimiento; 
    @FXML private TextField txtEdad; 
    @FXML private TextField txtDireccion; 
    @FXML private TextField txtOcupacion;
    @FXML private TextField txtSexo;
    @FXML private TableView tblPacientes;
    @FXML private TableColumn colCodigoPacientes;
    @FXML private TableColumn colNombres;
    @FXML private TableColumn colApellidos;
    @FXML private TableColumn colDPI;
    @FXML private TableColumn colNacimiento;
    @FXML private TableColumn colEdad;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colOcupacion;
    @FXML private TableColumn colSexo;
    @FXML private Button btnAgregar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigoPacientes.setItems(getPacientes());
        
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
     
    
    
    public void cargarDatos(){
        tblPacientes.setItems(getPacientes());
        colCodigoPacientes.setCellValueFactory(new PropertyValueFactory <Pacientes, Integer>("codigoPaciente"));
        colDPI.setCellValueFactory(new PropertyValueFactory <Pacientes,String>("DPI"));
        colNombres.setCellValueFactory(new PropertyValueFactory <Pacientes, String>("nombres"));
        colApellidos.setCellValueFactory(new PropertyValueFactory <Pacientes, String>("apellidos"));
        colNacimiento.setCellValueFactory(new PropertyValueFactory <Pacientes, String>("fechaNacimiento"));
        colEdad.setCellValueFactory(new PropertyValueFactory <Pacientes, Integer>("edad"));
        colDireccion.setCellValueFactory(new PropertyValueFactory <Pacientes, String>("direccion"));
        colOcupacion.setCellValueFactory(new PropertyValueFactory <Pacientes, String>("ocupacion"));
        colSexo.setCellValueFactory(new PropertyValueFactory <Pacientes, String>("sexo"));
        
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
    
    
    public void seleccionar(){
        Pacientes pacienteSeleccionado = (Pacientes) tblPacientes.getSelectionModel().getSelectedItem();
        cmbCodigoPacientes.getSelectionModel().select(pacienteSeleccionado.toString());
        txtDPI.setText(pacienteSeleccionado.getDPI());
        txtNombres.setText(pacienteSeleccionado.getNombres());
        txtApellidos.setText(pacienteSeleccionado.getApellidos());
        txtSexo.setText(pacienteSeleccionado.getSexo());
        txtNacimiento.setText(pacienteSeleccionado.getFechaNacimiento());
        txtEdad.setText(String.valueOf(pacienteSeleccionado.getEdad()));
        txtOcupacion.setText(pacienteSeleccionado.getOcupacion());
        txtDireccion.setText(pacienteSeleccionado.getDireccion());
    }
    
    
    
    private void ingresar() {
       Pacientes registro = new Pacientes();
       registro.setDPI(txtDPI.getText());
       registro.setNombres(txtNombres.getText());
       registro.setApellidos(txtApellidos.getText());
       registro.setFechaNacimiento(txtNacimiento.getText());
       registro.setEdad(Integer.parseInt(txtEdad.getText()));
       registro.setDireccion(txtDireccion.getText());
       registro.setOcupacion(txtOcupacion.getText());
       registro.setSexo(txtSexo.getText());
       
       try{
          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarPacientes (?,?,?,?,?,?,?,?)}"); 
          procedimiento.setString(1,registro.getDPI());
          procedimiento.setString(2,registro.getNombres());
          procedimiento.setString(3,registro.getApellidos());
          procedimiento.setString(4,registro.getFechaNacimiento());
          procedimiento.setInt(5,registro.getEdad());
          procedimiento.setString(6, registro.getDireccion());
          procedimiento.setString(7, registro.getDireccion());
          procedimiento.setString(8, registro.getSexo());
          procedimiento.execute();
          listarPacientes.add(registro);
       }catch(SQLException e){
       
           e.printStackTrace();
           
       }
       
        
    }
        
        
    private void limpiar() {
       txtDPI.setText("");
       txtNombres.setText("");
       txtApellidos.setText("");
       txtSexo.setText("");
       txtNacimiento.setText("");
       txtEdad.setText("");
       txtOcupacion.setText("");
       txtDireccion.setText("");
    }    
        
        
    private void activar() {
        txtNombres.setDisable(false);
        txtApellidos.setDisable(false);
        txtDPI.setDisable(false);
        txtNacimiento.setDisable(false);
        txtEdad.setDisable(false);
        txtOcupacion.setDisable(false);
        txtDireccion.setDisable(false);
        txtSexo.setDisable(false);
        
        cmbCodigoPacientes.setEditable(true);
        txtNombres.setEditable(true);
        txtApellidos.setEditable(true);
        txtDPI.setEditable(true);
        txtSexo.setEditable(true);
        txtOcupacion.setEditable(true);
        txtDireccion.setEditable(true);
        txtEdad.setEditable(true);
        txtNacimiento.setEditable(true);
        
        
    }
        
        
        
        
    private void desactivar() {
        cmbCodigoPacientes.setDisable(true);
        txtNombres.setDisable(true);
        txtApellidos.setDisable(true);
        txtDPI.setDisable(true);
        txtNacimiento.setDisable(true);
        txtEdad.setDisable(true);
        txtOcupacion.setDisable(true);
        txtDireccion.setDisable(true);
        txtSexo.setDisable(true);
        btnAgregar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnReporte.setDisable(false);        
        
        cmbCodigoPacientes.setEditable(false);
        txtNombres.setEditable(false);
        txtApellidos.setEditable(false);
        txtDPI.setEditable(false);
        txtSexo.setEditable(false);
        txtOcupacion.setEditable(false);
        txtDireccion.setEditable(false);
        txtEdad.setEditable(false);
        txtNacimiento.setEditable(false);
    }    
        
    
      public void nuevo(){
        switch(tipoDeOperaciones){
            case  Ninguno:
                cmbCodigoPacientes.setDisable(true);
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
        PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarPacientes (?,?,?,?,?,?,?,?,?)}");
        Pacientes registro = (Pacientes) tblPacientes.getSelectionModel().getSelectedItem();
   
        
       registro.setCodigoPaciente(registro.getCodigoPaciente());
       registro.setDPI(txtDPI.getText());
       registro.setNombres(txtNombres.getText());
       registro.setApellidos(txtApellidos.getText());
       registro.setFechaNacimiento(txtNacimiento.getText());
       registro.setEdad(Integer.parseInt(txtEdad.getText()));
       registro.setDireccion(txtDireccion.getText());
       registro.setOcupacion(txtOcupacion.getText());
       registro.setSexo(txtSexo.getText());
       
       
        procedimiento.setInt(1,registro.getCodigoPaciente());
        procedimiento.setString(2,registro.getDPI());
        procedimiento.setString(3,registro.getNombres());
        procedimiento.setString(4,registro.getApellidos());
        procedimiento.setString(5,registro.getFechaNacimiento());
        procedimiento.setInt(6,registro.getEdad());
        procedimiento.setString(7, registro.getDireccion());
        procedimiento.setString(8, registro.getOcupacion());
        procedimiento.setString(9, registro.getSexo());
        procedimiento.execute();
        listarPacientes.add(registro);
      
       
           
           
       }
        
   public void editar() throws SQLException{
        switch(tipoDeOperaciones){
            case Ninguno:
                if (tblPacientes.getSelectionModel().getSelectedItem() != null){
                    cmbCodigoPacientes.setDisable(false);
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
                
        }

    
    }
   
     public void cancelar(){
        btnAgregar.setText("Nuevo");
        btnEliminar.setText("Eliminar");
        btnReporte.setDisable(false);
        btnModificar.setDisable(false);
    
    }
     
     
     public void eliminar(){
        if(tipoDeOperaciones == operaciones.Guardar){
            cancelar();
            desactivar();
            tipoDeOperaciones = operaciones.Ninguno;
           
        }else{
            tipoDeOperaciones = operaciones.Eliminar;
            if(tblPacientes.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null,"Seguro que desea eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                 try{
                     PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarPacientes (?)}");
                     procedimiento.setInt(1,((Pacientes)tblPacientes.getSelectionModel().getSelectedItem()).getCodigoPaciente());
                     procedimiento.execute();
                     listarPacientes.remove(tblPacientes.getSelectionModel().getSelectedIndex());
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
                if(tblPacientes.getSelectionModel().getSelectedItem() != null){
                    int codigoPaciente = (((Pacientes)tblPacientes.getSelectionModel().getSelectedItem()).getCodigoPaciente());
                    Map parametros = new HashMap();
                    parametros.put("p_codigoPaciente", codigoPaciente);
                    GenerarReporte.mostrarReporte("PacientesReporte.jasper","Reporte Pacientes",parametros);      
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro a imprimir");
            }
        }            
    }  
    
    public void imprimirReporteGeneral(){
            int codigoPaciente = 0;
            Map parametros = new HashMap();
            parametros.put("codigoPaciente", codigoPaciente);
            GenerarReporte.mostrarReporte("PacientesReporteGeneral.jasper","Reporte Pacientes",parametros);      
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
    
    public void ventanaContacto(){
        escenarioPrincipal.ventanaContacto();
    }
}
