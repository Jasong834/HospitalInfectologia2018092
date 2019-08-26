package org.jasongatica.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.jasongatica.sistema.Principal;
public class MenuPrincipalController implements Initializable{
    private Principal escenarioPrincipal;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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
    
    
    public void ventanaProgramador(){
        escenarioPrincipal.ventanaProgramador();
    }
    
    public void ventanaPacientes(){
        escenarioPrincipal.ventanaPacientes();
    }
  
    public void ventanaArea(){
        escenarioPrincipal.ventanaArea();
    }
    
    public void ventanaCargo(){
        escenarioPrincipal.ventanaCargo();
    } 
    
    public void ventanaEspecialidad(){
        escenarioPrincipal.ventanaEspecialidad();
    }   
    
    public void ventanaHorarios(){
        escenarioPrincipal.ventanaHorarios();
    }
    
    public void ventanaResponsable(){
        escenarioPrincipal.ventanaResponsable();
    }
    
    public void ventanTurno(){
        escenarioPrincipal.ventanaTurno();
    }
}

