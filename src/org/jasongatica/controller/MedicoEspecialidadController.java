
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
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import org.jasongatica.bean.Especialidad;
import org.jasongatica.bean.Horario;
import org.jasongatica.bean.Medico;
import org.jasongatica.bean.MedicoEspecialidad;
import org.jasongatica.db.Conexion;
import org.jasongatica.report.GenerarReporte;
import org.jasongatica.sistema.Principal;


public class MedicoEspecialidadController implements Initializable{
    private Principal escenarioPrincipal;
    private enum operaciones{Nuevo,Guardar,Editar,Actualizar,Eliminar,Cancelar,Ninguno} 
    private operaciones tipoDeOperaciones = operaciones.Ninguno;    
    private ObservableList<Medico> listarMedico;
    private ObservableList<Horario> listarHorario;
    private ObservableList<Especialidad> listarEspecialidad;
    private ObservableList<MedicoEspecialidad> listarMedicoEspecialidad;
    
    @FXML private ComboBox cmbCodigo;
    @FXML private ComboBox cmbEspecialidad;
    @FXML private ComboBox cmbHorario;
    @FXML private ComboBox cmbMedico;
    @FXML private Button btnAgregar;
    @FXML private Button btnModificar;
    @FXML private Button btnEliminar;
    @FXML private Button btnReporte;
    @FXML private TableView tblEspecialidadMedicos;
    @FXML private TableColumn colCodigo;
    @FXML private TableColumn colEspecialidad;
    @FXML private TableColumn colHorario;
    @FXML private TableColumn colMedico;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
        cmbCodigo.setItems(getMedicoEspecialidad());
        cmbEspecialidad.setItems(getEspecialidad());
        cmbHorario.setItems(getHorario());
        cmbMedico.setItems(getMedicos());
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
      return listarMedico = FXCollections.observableList(lista);
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
    
    public ObservableList <Horario> getHorario() {
        ArrayList<Horario> lista = new ArrayList<Horario>();
        
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_ListarHorarios}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Horario(resultado.getInt("codigoHorario"),
                resultado.getString("horarioInicio"),
                resultado.getString("horarioSalida"),
                resultado.getInt("lunes"),
                resultado.getInt("martes"),
                resultado.getInt("miercoles"),
                resultado.getInt("jueves"),
                resultado.getInt("viernes")));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
      return listarHorario = FXCollections.observableList(lista);
    }    
    
    public Horario buscarHorario(int codigoHorario){
        Horario resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_BuscarHorarios (?)}");
            procedimiento.setInt(1, codigoHorario);
            ResultSet registro = procedimiento.executeQuery();
            
            while(registro.next()){
            resultado = new Horario(registro.getInt("codigoHorario"),
            registro.getString("horarioInicio"),
            registro.getString("horarioSalida"),
            registro.getInt("lunes"),
            registro.getInt("martes"),
            registro.getInt("miercoles"),
            registro.getInt("jueves"),
            registro.getInt("viernes")); 
            }
        }catch(SQLException e){
            e.printStackTrace();
            e.getMessage();
        
        }
        return resultado;
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
    
    public void cargarDatos(){
        tblEspecialidadMedicos.setItems(getMedicoEspecialidad());
        colCodigo.setCellValueFactory(new PropertyValueFactory <MedicoEspecialidad, Integer>("codigoMedicoEspecialidad"));
        colEspecialidad.setCellValueFactory(new PropertyValueFactory <MedicoEspecialidad,Integer>("codigoEspecialidad"));
        colHorario.setCellValueFactory(new PropertyValueFactory <MedicoEspecialidad, Integer>("codigoHorario"));
        colMedico.setCellValueFactory(new PropertyValueFactory <MedicoEspecialidad, Integer>("codigoMedico"));   
    }        
    
    public void seleccionar(){
        MedicoEspecialidad medicoEspecialidadSeleccionado = (MedicoEspecialidad) tblEspecialidadMedicos.getSelectionModel().getSelectedItem();
        cmbCodigo.getSelectionModel().select(medicoEspecialidadSeleccionado.toString());
        
        int codigoEspecialidad = medicoEspecialidadSeleccionado.getCodigoEspecialidad();
        Especialidad especialidadSeleccionado = buscarEspecialidad(codigoEspecialidad);
        cmbEspecialidad.getSelectionModel().select(especialidadSeleccionado.getCodigoEspecialidad() + " - " + especialidadSeleccionado.getNombreEspecialidad());
    
        int codigoHorario= medicoEspecialidadSeleccionado.getCodigoHorario();
        Horario horarioSeleccionado = buscarHorario(codigoHorario);
        cmbHorario.getSelectionModel().select(horarioSeleccionado.getCodigoHorario() + " - " + horarioSeleccionado.getHorarioInicio() + " " + horarioSeleccionado.getHorarioSalida());
    
        int codigoMedico = medicoEspecialidadSeleccionado.getCodigoMedico();
        Medico medicoSeleccionado = buscarMedico(codigoMedico);
        cmbMedico.getSelectionModel().select(medicoSeleccionado.getCodigoMedico()+ " - " + medicoSeleccionado.getNombres() + " " + medicoSeleccionado.getApellidos());
    }
    
    private void ingresar() {
       MedicoEspecialidad registro = new MedicoEspecialidad();
       String codigoEspecialidad,codigoEspecialidad1,codigoHorario,codigoHorario1,codigoMedico,codigoMedico1;
        
       Integer posicion;
       codigoEspecialidad = cmbEspecialidad.getValue().toString();
       posicion = codigoEspecialidad.indexOf("-");
       codigoEspecialidad1 = codigoEspecialidad.substring(0,posicion);
       registro.setCodigoEspecialidad(Integer.parseInt(codigoEspecialidad1));
       
       Integer posicion2;
       codigoHorario = cmbHorario.getValue().toString();
       posicion2 = codigoHorario.indexOf("-");
       codigoHorario1 = codigoHorario.substring(0,posicion);
       registro.setCodigoHorario(Integer.parseInt(codigoHorario1));
       
       Integer posicion3;
       codigoMedico = cmbMedico.getValue().toString();
       posicion3 = codigoMedico.indexOf("-");
       codigoMedico1 = codigoMedico.substring(0,posicion);
       registro.setCodigoMedico(Integer.parseInt(codigoMedico1));       

       
       try{
          PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_AgreagarMedicoEspecialida (?,?,?)}"); 
          procedimiento.setInt(1,Integer.parseInt(codigoEspecialidad1));
          procedimiento.setInt(2,Integer.parseInt(codigoHorario1));
          procedimiento.setInt(3,Integer.parseInt(codigoMedico1));
   
          procedimiento.execute();
          listarMedicoEspecialidad.add(registro);
       }catch(SQLException e){
       
           e.printStackTrace(); 
       }  
    }  
    
    
    
    private void activar() {
        cmbEspecialidad.setDisable(false);
        cmbHorario.setDisable(false);
        cmbMedico.setDisable(false);
        
        cmbEspecialidad.setEditable(true);
        cmbHorario.setEditable(true);
        cmbMedico.setEditable(true);
    }  
    
    

    
    private void desactivar() {
        cmbCodigo.setDisable(true);
        cmbEspecialidad.setDisable(true);
        cmbHorario.setDisable(true);
        cmbMedico.setDisable(true); 
        btnAgregar.setDisable(false);
        btnEliminar.setDisable(false);
        btnModificar.setDisable(false);
        btnReporte.setDisable(false);        
        
        cmbEspecialidad.setEditable(false);
        cmbHorario.setEditable(false);
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
        PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call  sp_ModificarrMedicoEspecialida (?,?,?,?)}");
        MedicoEspecialidad registro= (MedicoEspecialidad) tblEspecialidadMedicos.getSelectionModel().getSelectedItem();
                
        registro.setCodigoMedicoEspecialidad(registro.getCodigoMedicoEspecialidad());
        registro.setCodigoEspecialidad(registro.getCodigoEspecialidad());
        registro.setCodigoHorario(registro.getCodigoHorario());
        registro.setCodigoMedico(registro.getCodigoMedico());
      
        procedimiento.setInt(1,registro.getCodigoMedicoEspecialidad());
        procedimiento.setInt(2,registro.getCodigoEspecialidad());
        procedimiento.setInt(3,registro.getCodigoHorario());
        procedimiento.setInt(4,registro.getCodigoMedico());
        procedimiento.execute();
        listarMedicoEspecialidad.add(registro);
       }   

    public void editar() throws SQLException{
        switch(tipoDeOperaciones){
            case Ninguno:
                if (tblEspecialidadMedicos.getSelectionModel().getSelectedItem() != null){
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
            if(tblEspecialidadMedicos.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null,"Seguro que desea eliminar el registro?","Eliminar",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                    try{
                     PreparedStatement procedimiento = Conexion.getInstancia().getConexion().prepareCall("{call sp_EliminarMedicoEspecialidad (?)}");
                     procedimiento.setInt(1,((MedicoEspecialidad)tblEspecialidadMedicos.getSelectionModel().getSelectedItem()).getCodigoMedicoEspecialidad());
                     procedimiento.execute();
                     listarMedicoEspecialidad.remove(tblEspecialidadMedicos.getSelectionModel().getSelectedIndex());
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
                if(tblEspecialidadMedicos.getSelectionModel().getSelectedItem() != null){
                    int codigoMedicoEspecialidad = (((MedicoEspecialidad)tblEspecialidadMedicos.getSelectionModel().getSelectedItem()).getCodigoMedicoEspecialidad());
                    Map parametros = new HashMap();
                    parametros.put("p_codigoMedicoEspecialidad", codigoMedicoEspecialidad);
                    GenerarReporte.mostrarReporte("ReporteMedicoESP.jasper","Reporte Especialidad del Medico",parametros);      
            }else{
                JOptionPane.showMessageDialog(null,"Debe seleccionar un registro a imprimir");
            }
        }
    }    

    public void imprimirReporteGeneral(){
        int codigoMedicoEspecialidad = 0;
        Map parametros = new HashMap();
        parametros.put("codigoMedicoEspecialidad", codigoMedicoEspecialidad);
        GenerarReporte.mostrarReporte("ReporteGeneralMedicoESP.jasper","Reporte Especialidad del Medico",parametros);      
    }      
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaMedico(){
        escenarioPrincipal.ventanaMedico();
    }
    
    public void ventanHorarios(){
        escenarioPrincipal.ventanaHorarios();
    }
    
    public void ventanaEspecialidad(){
        escenarioPrincipal.ventanaEspecialidad();
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}
