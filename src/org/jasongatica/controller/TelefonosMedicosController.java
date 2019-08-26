
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
import org.jasongatica.bean.Medico;
import org.jasongatica.bean.TelefonosMedicos;
import org.jasongatica.db.Conexion;
import org.jasongatica.report.GenerarReporte;
import org.jasongatica.sistema.Principal;


public class TelefonosMedicosController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{Nuevo,Guardar,Editar,Actualizar,Eliminar,Cancelar,Ninguno} 
    private operaciones tipoDeOperaciones = operaciones.Ninguno;
    private ObservableList<Medico> listarMedicos;
    private ObservableList<TelefonosMedicos> listarTelefonosMedicos;
    
    @FXML private TextField txtPersonal;
    @FXML private TextField txtTrabajo;
    @FXML private ComboBox cmbCodigoTelefono;
    @FXML private ComboBox cmbMedico;
    @FXML private TableView tblTelefonosMedicos;
    @FXML private TableColumn colCodigoTelefonos;
    @FXML private TableColumn colPersonal;
    @FXML private TableColumn colTrabajo;
    @FXML private TableColumn colMedico;
    @FXML private Button btnAgregar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbMedico.setItems(getMedicos());
        cmbCodigoTelefono.setItems(getTelefonosMedicos());
       
        cargarDatos();
    }

    
    public ObservableList <TelefonosMedicos> getTelefonosMedicos() {
        ArrayList<TelefonosMedicos> lista = new ArrayList<TelefonosMedicos>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTelefonosMedicos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TelefonosMedicos(resultado.getInt("codigoTelefonoMedico"),
                resultado.getString("telefonoPersonal"),
                resultado.getString("telefonoTrabajo"),
                resultado.getInt("codigoMedico")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
      return listarTelefonosMedicos = FXCollections.observableList(lista);
    } 
    
    
    public TelefonosMedicos buscarTelefonos(int codigoTelefonosMedicos){
            TelefonosMedicos resultado = null;
            try{
                PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarTelefonosMedicos (?)}");
                procedimiento.setInt(1,codigoTelefonosMedicos);
                ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
                resultado = new TelefonosMedicos(registro.getInt("codigoTelefonoMedico"),
                registro.getString("telefonoPersonal"),
                registro.getString("telefonoTrabajo"),
                registro.getInt("codigoMedico"));
            
                }
            }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        
        }
        return resultado;
    }
        
    public ObservableList <Medico> getMedicos() {
       ArrayList<Medico> lista = new ArrayList<Medico>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarMedicos}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Medico(resultado.getInt("codigoMedico"),
                resultado.getInt("licenciaMedica"),
                resultado.getString("nombres"),
                resultado.getString("apellidos"),
                resultado.getString("horaEntrada"),
                resultado.getString("horaSalida"),
                resultado.getString("sexo")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
      return listarMedicos = FXCollections.observableList(lista);
    }     
    
    
    public Medico buscarMedico(int codigoMedico){
        Medico resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarMedico (?)}");
            procedimiento.setInt(1, codigoMedico);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
            resultado = new Medico(registro.getInt("codigoMedico"),
            registro.getInt("licenciaMedica"),
            registro.getString("nombres"),
            registro.getString("apellidos"),
            registro.getString("horaEntrada"),
            registro.getString("horaSalida"),
            registro.getString("sexo"));
            
            }
        }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        
        }
        return resultado;
    }
        
    public void cargarDatos(){
        
        tblTelefonosMedicos.setItems(getTelefonosMedicos());
        colCodigoTelefonos.setCellValueFactory(new PropertyValueFactory <TelefonosMedicos, Integer>("codigoTelefonoMedico"));
        colPersonal.setCellValueFactory(new PropertyValueFactory <TelefonosMedicos,String>("telefonoPersonal"));
        colTrabajo.setCellValueFactory(new PropertyValueFactory <TelefonosMedicos, String>("telefonoTrabajo"));
        colMedico.setCellValueFactory(new PropertyValueFactory <TelefonosMedicos, Integer>("codigoMedico"));
        
    }
        
    public void seleccionar(){
        TelefonosMedicos telefonoSeleccionado = (TelefonosMedicos) tblTelefonosMedicos.getSelectionModel().getSelectedItem();
        cmbCodigoTelefono.getSelectionModel().select(telefonoSeleccionado.toString());
        txtPersonal.setText(telefonoSeleccionado.getTelefonoPersonal());
        txtTrabajo.setText(telefonoSeleccionado.getTelefonoTrabajo());
        
        int codigoMedico = telefonoSeleccionado.getCodigoMedico();
        Medico medicoSeleccionado = buscarMedico(codigoMedico);
        
        cmbMedico.getSelectionModel().select(medicoSeleccionado.getCodigoMedico() + " - " + medicoSeleccionado.getNombres() + " " + medicoSeleccionado.getApellidos());
    }
    
    
    
    private void ingresar() {
       TelefonosMedicos registro = new TelefonosMedicos();
       registro.setTelefonoPersonal(txtPersonal.getText());
       registro.setTelefonoTrabajo(txtTrabajo.getText());
      //  cmbMedico.getSelectionModel().getSelectedItem().toString()
       String codigoMedico,codigoMedico1;
        
       Integer posicion;
       codigoMedico = cmbMedico.getValue().toString();
       posicion = codigoMedico.indexOf("-");
       codigoMedico1 = codigoMedico.substring(0,posicion);
       registro.setCodigoMedico(Integer.parseInt(codigoMedico1));

       
       try{
          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarTelefonoMedicos (?,?,?)}"); 
          procedimiento.setString(1,registro.getTelefonoPersonal());
          procedimiento.setString(2,registro.getTelefonoTrabajo());
          procedimiento.setInt(3,Integer.parseInt(codigoMedico1));
          procedimiento.execute();
          listarTelefonosMedicos.add(registro);
       }catch(SQLException e){
       
           e.printStackTrace();
           
       }
       
        
    }
    
    
    private void limpiar() {
       txtPersonal.setText("");
       txtTrabajo.setText("");
    }
    
    private void activar() {
        txtPersonal.setDisable(false);
        txtTrabajo.setDisable(false);
        cmbMedico.setDisable(false);
        
        cmbCodigoTelefono.setEditable(true);
        txtPersonal.setEditable(true);
        txtTrabajo.setEditable(true);
        cmbMedico.setEditable(true);
    } 
    
    private void desactivar() {
        cmbCodigoTelefono.setDisable(true);
        txtPersonal.setDisable(true);
        txtTrabajo.setDisable(true);
        cmbMedico.setDisable(true);
        btnAgregar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnReporte.setDisable(false);        
        
        cmbCodigoTelefono.setEditable(false);
        txtPersonal.setEditable(false);
        txtTrabajo.setEditable(false);
        cmbMedico.setEditable(false);
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
                cmbCodigoTelefono.setDisable(true);
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
        PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ModificarTelefonoMedicos (?,?,?,?)}");
        TelefonosMedicos registro= (TelefonosMedicos) tblTelefonosMedicos.getSelectionModel().getSelectedItem();
        
        //JOptionPane.showMessageDialog(null,cmbCodigoMedico.getValue().toString());
        
       registro.setCodigoTelefonoMedico(registro.getCodigoTelefonoMedico());
       registro.setTelefonoPersonal(txtPersonal.getText());
       registro.setTelefonoTrabajo(txtTrabajo.getText());
       registro.setCodigoMedico(registro.getCodigoMedico());
      
        procedimiento.setInt(1,registro.getCodigoTelefonoMedico());
        procedimiento.setString(2,registro.getTelefonoPersonal());
        procedimiento.setString(3,registro.getTelefonoTrabajo());
        procedimiento.setInt(4,registro.getCodigoMedico());
        procedimiento.execute();
        listarTelefonosMedicos.add(registro);

       }   
          
          
    public void editar() throws SQLException{
        switch(tipoDeOperaciones){
            case Ninguno:
                if (tblTelefonosMedicos.getSelectionModel().getSelectedItem() != null){
                    cmbCodigoTelefono.setDisable(false);
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
            if(tblTelefonosMedicos.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null,"Seguro que desea eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                    try{
                     PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarTelefonoMedicos (?)}");
                     procedimiento.setInt(1,((TelefonosMedicos)tblTelefonosMedicos.getSelectionModel().getSelectedItem()).getCodigoTelefonoMedico());
                     procedimiento.execute();
                     listarTelefonosMedicos.remove(tblTelefonosMedicos.getSelectionModel().getSelectedIndex());
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
                if(tblTelefonosMedicos.getSelectionModel().getSelectedItem() != null){
                    int codigoTelefonoMedico = (((TelefonosMedicos)tblTelefonosMedicos.getSelectionModel().getSelectedItem()).getCodigoTelefonoMedico());
                    Map parametros = new HashMap();
                    parametros.put("codigoTelefonoMedico", codigoTelefonoMedico);
                    GenerarReporte.mostrarReporte("ReporteTelefonos.jasper","Reporte Telefonos de Medicos",parametros);      
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro a imprimir");
            }
        }        
    }        
       
    public void imprimirReporteGeneral(){
            int codigoTelefonoMedico = 0;
            Map parametros = new HashMap();
            parametros.put("codigoTelefonoMedico", codigoTelefonoMedico);
            GenerarReporte.mostrarReporte("ReporteGeneralTelefonosjasper","Debe seleccionar un registro a imprimir",parametros);      
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
    
    public void ventanaMedicos(){
        escenarioPrincipal.ventanaMedico();
    }
}
