package org.jasongatica.sistema;

import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jasongatica.bean.MedicoEspecialidad;
import org.jasongatica.controller.AreasController;
import org.jasongatica.controller.CargoController;
import org.jasongatica.controller.ContactoUrgenciaController;
import org.jasongatica.controller.EspecialidadController;
import org.jasongatica.controller.HorarioController;
import org.jasongatica.controller.MedicoController;
import org.jasongatica.controller.MedicoEspecialidadController;
import org.jasongatica.controller.MenuPrincipalController;
import org.jasongatica.controller.PacientesController;
import org.jasongatica.controller.ProgramadorController;
import org.jasongatica.controller.ResponsableTurnoController;
import org.jasongatica.controller.TelefonosMedicosController;
import org.jasongatica.controller.TurnoController;
import org.jasongatica.db.Conexion;


public class Principal extends Application {
    private final String PAQUETE_VISTA = "/org/jasongatica/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal)throws Exception {
        this.escenarioPrincipal = escenarioPrincipal;
        escenarioPrincipal.setTitle("Hospital De Infectología");
        menuPrincipal();
        escenarioPrincipal.show();
        
    }
    
    
        public Initializable cambiarEscena(String fxml,int ancho,int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
      
    
    public void menuPrincipal(){
        try{
           MenuPrincipalController menuPrincipal = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml",600,400);
           menuPrincipal.setEscenarioPrincipal(this);
           
        }catch(Exception e){
            e.printStackTrace();
        }    
    }

    public void ventanaMedico() {
        try{
           MedicoController medicoController = (MedicoController)cambiarEscena("MedicosView.fxml",602,512);
           medicoController.setEscenarioPrincipal(this);
           
            }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaProgramador(){
            try{
           ProgramadorController programadorController = (ProgramadorController)cambiarEscena("ProgramadorView.fxml",602,405);
           programadorController.setEscenarioPrincipal(this);
           
            }catch(Exception e){
            e.printStackTrace();
        }
    }
  
    
    
     public void ventanaTelefonosMedicos(){
            try{
           TelefonosMedicosController telefonoController = (TelefonosMedicosController)cambiarEscena("TelefonosMedicosView.fxml",587,413);
           telefonoController.setEscenarioPrincipal(this);
           
            }catch(Exception e){
            e.printStackTrace();
        }
    } 
    
     
    public void ventanaPacientes(){
            try{
           PacientesController pacientesController = (PacientesController)cambiarEscena("PacientesView.fxml",757,503);
           pacientesController.setEscenarioPrincipal(this);
           
            }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void ventanaArea(){
        try{
           AreasController areasController = (AreasController)cambiarEscena("AreasView.fxml",491,400);
           areasController.setEscenarioPrincipal(this);
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
   
    public void ventanaCargo(){
        try{
           CargoController cargoController = (CargoController)cambiarEscena("CargoView.fxml",537,374);
           cargoController.setEscenarioPrincipal(this);
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaEspecialidad(){
        try{
           EspecialidadController especialidadController = (EspecialidadController)cambiarEscena("EspecialidadView.fxml",600,400);
           especialidadController.setEscenarioPrincipal(this);
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaHorarios(){
        try{
           HorarioController horarioController = (HorarioController)cambiarEscena("HorariosView.fxml",633,502);
           horarioController.setEscenarioPrincipal(this);
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void ventanaContacto(){
        try{
           ContactoUrgenciaController contactoController = (ContactoUrgenciaController)cambiarEscena("ContactoUrgenciaView.fxml",633,502);
           contactoController.setEscenarioPrincipal(this);
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
    
    public void ventanaMedicoEspecialidad(){
        try{
           MedicoEspecialidadController medicoEspecialidadController = (MedicoEspecialidadController)cambiarEscena("MedicosEspecialidadView.fxml",617,406);
           medicoEspecialidadController.setEscenarioPrincipal(this);
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }  
    
    public void ventanaResponsable(){
        try{
           ResponsableTurnoController responsableController = (ResponsableTurnoController)cambiarEscena("ResponsableTurnoView.fxml",647,472);
           responsableController.setEscenarioPrincipal(this);
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }      
    
    public void ventanaTurno(){
        try{
           TurnoController turnoController = (TurnoController)cambiarEscena("TurnoView.fxml",643,525);
           turnoController.setEscenarioPrincipal(this);
           
        }catch(Exception e){
            e.printStackTrace();
        }
    }     
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
