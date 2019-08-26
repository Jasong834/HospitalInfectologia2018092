package org.jasongatica.bean;


public class TelefonosMedicos {
    private int codigoTelefonoMedico;
    private String telefonoPersonal;
    private String telefonoTrabajo;
    private int codigoMedico;

    public TelefonosMedicos(int codigoTelefonoMedico, String telefonoPersonal, String telefonoTrabajo, int codigoMedico) {
        this.codigoTelefonoMedico = codigoTelefonoMedico;
        this.telefonoPersonal = telefonoPersonal;
        this.telefonoTrabajo = telefonoTrabajo;
        this.codigoMedico = codigoMedico;
    }

    
    
    public TelefonosMedicos() {
    }

    public int getCodigoTelefonoMedico() {
        return codigoTelefonoMedico;
    }

    public void setCodigoTelefonoMedico(int codigoTelefonoMedico) {
        this.codigoTelefonoMedico = codigoTelefonoMedico;
    }

    public String getTelefonoPersonal() {
        return telefonoPersonal;
    }

    public void setTelefonoPersonal(String telefonoPersonal) {
        this.telefonoPersonal = telefonoPersonal;
    }

    public String getTelefonoTrabajo() {
        return telefonoTrabajo;
    }

    public void setTelefonoTrabajo(String telefonoTrabajo) {
        this.telefonoTrabajo = telefonoTrabajo;
    }

    public int getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(int codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    

    @Override
    public  String toString() {

        return  "" + codigoTelefonoMedico + "-";
    }
    
    
}
