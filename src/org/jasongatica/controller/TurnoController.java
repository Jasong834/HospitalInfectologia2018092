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
import org.jasongatica.bean.MedicoEspecialidad;
import org.jasongatica.bean.Pacientes;
import org.jasongatica.bean.ResponsableTurno;
import org.jasongatica.bean.Turno;
import org.jasongatica.db.Conexion;
import org.jasongatica.report.GenerarReporte;
import org.jasongatica.sistema.Principal;


public class TurnoController implements Initializable{


    private Principal escenarioPrincipal;
    private enum operaciones{Nuevo,Guardar,Editar,Actualizar,Eliminar,Cancelar,Ninguno} 
    private operaciones tipoDeOperaciones = operaciones.Ninguno;  
    private ObservableList<ResponsableTurno> listarResponsable;
    private ObservableList<Pacientes> listarPacientes;
    private ObservableList<Turno> listarTurno;
    private ObservableList<MedicoEspecialidad> listarMedicoEspecialidad;

    
    @FXML private ComboBox cmbCodigo;
    @FXML private ComboBox cmbResponsable;
    @FXML private ComboBox cmbPaciente;
    @FXML private ComboBox cmbEpecialidadMedico;
    @FXML private TextField txtFecha;
    @FXML private TextField txtCita;
    @FXML private TextField txtValor;
    @FXML private Button btnAgregar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblTurno;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colFecha;
    @FXML private TableColumn colCita;
    @FXML private TableColumn colValor;
    @FXML private TableColumn colResponsable;
    @FXML private TableColumn colPaciente;
    @FXML private TableColumn colEspecialidad;    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigo.setItems(getTurno());
        cmbResponsable.setItems(getResponsable());
        cmbPaciente.setItems(getPacientes());
        cmbEpecialidadMedico.setItems(getMedicoEspecialidad());
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
    
    public ObservableList<MedicoEspecialidad> getMedicoEspecialidad(){
        ArrayList lista = new ArrayList<MedicoEspecialidad>();
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarMedicoEspecialidad}");
            ResultSet resultado =procedimiento.executeQuery();
            
            while(resultado.next()){
               lista.add(new MedicoEspecialidad(resultado.getInt("codigoMedicoEspecialidad"),
               resultado.getInt("codigoEspecialidad"),
               resultado.getInt("codigoHorario"),
               resultado.getInt("codigoMedico")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return listarMedicoEspecialidad = FXCollections.observableList(lista);
    }

    private MedicoEspecialidad buscarMedicoEspecialidad(int codigoMedicoEspecialidad){
        MedicoEspecialidad resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarMedicoEspecialidad (?)}");
            procedimiento.setInt(1,codigoMedicoEspecialidad);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
                resultado = new MedicoEspecialidad(registro.getInt("codigoMedicoEspecialidad"),
                registro.getInt("codigoEspecialidad"),
                registro.getInt("codigoHorario"),
                registro.getInt("codigoMedico"));  
            }
       }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        }
        return resultado;
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
   
     
     public ObservableList <Turno> getTurno() {
        ArrayList<Turno> lista = new ArrayList<Turno>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarTurno}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Turno(resultado.getInt("codigoTurno"),
                resultado.getString("fechaTurno"),
                resultado.getString("fecharCita"),
                resultado.getFloat("valorCita"),
                resultado.getInt("codigoResponsableTurno"),
                resultado.getInt("codigoPaciente"),
                resultado.getInt("codigoMedicoEspecialidad")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
      return listarTurno = FXCollections.observableList(lista);
    }    
    
     public Turno buscarTurno(int codigoTurno){
        Turno resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarTurno (?)}");
            procedimiento.setInt(1, codigoTurno);
            ResultSet registro = procedimiento.executeQuery();
            
           while(registro.next()){
                resultado = new Turno(registro.getInt("codigoTurno"),
                registro.getString("fechaTurno"),
                registro.getString("fecharCita"),
                registro.getFloat("valorCita"),
                registro.getInt("codigoResponsableTurno"),
                registro.getInt("codigoPaciente"),
                registro.getInt("codigoMedicoEspecialidad"));
            
            }
        }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        
        }
        return resultado;
    }      

    public void cargarDatos(){
        tblTurno.setItems(getTurno());
        colCodigo.setCellValueFactory(new PropertyValueFactory <Turno, Integer>("codigoTurno"));
        colFecha.setCellValueFactory(new PropertyValueFactory <Turno,String>("fechaTurno"));
        colCita.setCellValueFactory(new PropertyValueFactory <Turno,String>("fecharCita"));
        colValor.setCellValueFactory(new PropertyValueFactory <Turno,Float>("valorCita"));   
        colResponsable.setCellValueFactory(new PropertyValueFactory <Turno, Integer>("codigoResponsableTurno"));
        colPaciente.setCellValueFactory(new PropertyValueFactory <Turno, Integer>("codigoPaciente"));
        colEspecialidad.setCellValueFactory(new PropertyValueFactory <Turno,Integer>("codigoMedicoEspecialidad"));
    }     

    public void seleccionar(){
        Turno turnoSeleccionado = (Turno) tblTurno.getSelectionModel().getSelectedItem();
        cmbCodigo.getSelectionModel().select(turnoSeleccionado.toString());
        txtFecha.setText(turnoSeleccionado.getFechaTurno());
        txtCita.setText(turnoSeleccionado.getFecharCita());
        txtValor.setText(String.valueOf(turnoSeleccionado.getValorCita()));
        
        int codigoResponsableTurno = turnoSeleccionado.getCodigoResponsableTurno();
        ResponsableTurno responsableSeleccionado = buscarResponsable(codigoResponsableTurno);
        cmbResponsable.getSelectionModel().select(responsableSeleccionado.getCodigoResponsableTurno()+" "+ responsableSeleccionado.getNombreResponsable() + " " +  responsableSeleccionado.getApellidoResponsable());
    
        int codigoPaciente= turnoSeleccionado.getCodigoPaciente();
        Pacientes pacienteSeleccionado = buscarPacientes(codigoPaciente);
        cmbPaciente.getSelectionModel().select(pacienteSeleccionado.getCodigoPaciente() + " - " + pacienteSeleccionado.getNombres()+ " " +pacienteSeleccionado.getApellidos());
        
        int codigoMedicoEspecialidad= turnoSeleccionado.getCodigoMedicoEspecialidad();
        MedicoEspecialidad medicoEspecialidadSeleccionado = buscarMedicoEspecialidad(codigoMedicoEspecialidad);
        cmbEpecialidadMedico.getSelectionModel().select(medicoEspecialidadSeleccionado.getCodigoMedicoEspecialidad());
    }    
    
    
    private void ingresar() {
       Turno registro = new Turno();
       registro.setFechaTurno(txtFecha.getText());
       registro.setFecharCita(txtCita.getText());
       registro.setValorCita(Float.valueOf(txtValor.getText()));
       
       String codigoPaciente,codigoPaciente1,codigoResponsableTurno,codigoResponsableTurno1,codigoMedicoEspecialidad,codigoMedicoEspecialidad1;
        
       Integer posicion;
       codigoPaciente = cmbPaciente.getValue().toString();
       posicion = codigoPaciente.indexOf("-");
       codigoPaciente1 = codigoPaciente.substring(0,posicion);
       registro.setCodigoPaciente(Integer.parseInt(codigoPaciente1));
       
       Integer posicion2;
       codigoResponsableTurno = cmbResponsable.getValue().toString();
       posicion2 = codigoResponsableTurno.indexOf("-");
       codigoResponsableTurno1 = codigoResponsableTurno.substring(0,posicion);
       registro.setCodigoResponsableTurno(Integer.parseInt(codigoResponsableTurno1));
       
       Integer posicion3;
       codigoMedicoEspecialidad = cmbEpecialidadMedico.getValue().toString();
       posicion2 = codigoMedicoEspecialidad.indexOf("-");
       codigoMedicoEspecialidad1 = codigoMedicoEspecialidad.substring(0,posicion);
       registro.setCodigoMedicoEspecialidad(Integer.parseInt(codigoMedicoEspecialidad1));

       
       try{
          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgregarTurno (?,?,?,?,?,?)}"); 
          procedimiento.setString(1,registro.getFechaTurno());
          procedimiento.setString(2,registro.getFecharCita());
          procedimiento.setFloat(3, registro.getValorCita());
          procedimiento.setInt(4,Integer.parseInt(codigoResponsableTurno1));
          procedimiento.setInt(5,Integer.parseInt(codigoPaciente1));
          procedimiento.setInt(6,Integer.parseInt(codigoMedicoEspecialidad1));
   
          procedimiento.execute();
          listarTurno.add(registro);
       }catch(SQLException e){
           e.printStackTrace(); 
       }  
    }     
    
    private void limpiar() {
       txtFecha.setText("");
       txtCita.setText("");
       txtValor.setText("");
    }    
        
        
    private void activar() {
        txtFecha.setDisable(false);
        txtCita.setDisable(false);
        txtValor.setDisable(false);
        cmbPaciente.setDisable(false);
        cmbResponsable.setDisable(false);
        cmbEpecialidadMedico.setDisable(false);

        cmbCodigo.setEditable(true);
        cmbPaciente.setEditable(true);
        cmbResponsable.setEditable(true);
        cmbEpecialidadMedico.setEditable(true);
        txtFecha.setEditable(true);
        txtValor.setEditable(true);
        txtCita.setEditable(true);
    }
        
        
        
        
    private void desactivar() {
        txtFecha.setDisable(true);
        txtCita.setDisable(true);
        txtValor.setDisable(true);
        cmbPaciente.setDisable(true);
        cmbResponsable.setDisable(true);
        cmbEpecialidadMedico.setDisable(true);
        cmbCodigo.setDisable(true);
        btnAgregar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnReporte.setDisable(false);        

        cmbCodigo.setEditable(false);
        cmbPaciente.setEditable(false);
        cmbResponsable.setEditable(false);
        cmbEpecialidadMedico.setEditable(false);
        txtFecha.setEditable(false);
        txtValor.setEditable(false);
        txtCita.setEditable(false);
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
        PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call  sp_ModificarTurno (?,?,?,?,?,?,?)}");
        Turno registro= (Turno) tblTurno.getSelectionModel().getSelectedItem();
                
        registro.setCodigoTurno(registro.getCodigoTurno());
        registro.setFechaTurno(registro.getFechaTurno());
        registro.setFecharCita(registro.getFecharCita());
        registro.setValorCita(registro.getValorCita());
        registro.setCodigoResponsableTurno(registro.getCodigoResponsableTurno());
        registro.setCodigoPaciente(registro.getCodigoPaciente());
      
        procedimiento.setInt(1,registro.getCodigoTurno());
        procedimiento.setString(2,registro.getFechaTurno());
        procedimiento.setString(3,registro.getFecharCita());
        procedimiento.setFloat(4,registro.getValorCita());
        procedimiento.setInt(5,registro.getCodigoResponsableTurno());
        procedimiento.setInt(6,registro.getCodigoPaciente());
        procedimiento.setInt(7,registro.getCodigoMedicoEspecialidad());
        procedimiento.execute();
        listarTurno.add(registro);
    }    
    
    public void editar() throws SQLException{
        switch(tipoDeOperaciones){
            case Ninguno:
                if (tblTurno.getSelectionModel().getSelectedItem() != null){
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
            if(tblTurno.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null,"Seguro que desea eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                 try{
                     PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarTurno (?)}");
                     procedimiento.setInt(1,((Turno)tblTurno.getSelectionModel().getSelectedItem()).getCodigoTurno());
                     procedimiento.execute();
                     listarTurno.remove(tblTurno.getSelectionModel().getSelectedIndex());
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
    
    //codigoResponsableTurno
    
    public void imprimirReporte(){
        if(tipoDeOperaciones == operaciones.Actualizar){
            cancelar();
            desactivar();
            btnReporte.setText("Reporte");
            tipoDeOperaciones = operaciones.Ninguno;
            }else{        
                if(tblTurno.getSelectionModel().getSelectedItem() != null){
                    int codigoResponsableTurno = (((Turno)tblTurno.getSelectionModel().getSelectedItem()).getCodigoTurno());
                    Map parametros = new HashMap();
                    parametros.put("p_codigoResponsableTurno", codigoResponsableTurno);
                    GenerarReporte.mostrarReporte("PacientesReporte.jasper","Reporte Turno",parametros);      
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro a imprimir");
            }
        }
    }     
    
    public void imprimirReporteGeneral(){
            int codigoTurno = 0;
            Map parametros = new HashMap();
            parametros.put("codigoTurno", codigoTurno);
            GenerarReporte.mostrarReporte("ReporteGeneralTurno.jasper","Reporte Turno",parametros);      
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

    public void ventanaMedicoEspecialidad(){
        escenarioPrincipal.ventanaMedicoEspecialidad();
    }
    
    public void venatanPacientes(){
        escenarioPrincipal.ventanaPacientes();
    }
    
    public void ventanResponsable(){
        escenarioPrincipal.ventanaResponsable();
    }
}

