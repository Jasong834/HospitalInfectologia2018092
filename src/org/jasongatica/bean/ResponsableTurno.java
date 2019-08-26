package org.jasongatica.bean;


public class ResponsableTurno {
    private int codigoResponsableTurno;
    private String nombreResponsable;
    private String apellidoResponsable;
    private String telefonResponsable;
    private int codigoArea;
    private int codigoCargo;

    public ResponsableTurno() {
    }

    public ResponsableTurno(int codigoResponsableTurno, String nombreResponsable, String apellidoResponsable, String telefonResponsable, int codigoArea, int codigoCargo) {
        this.codigoResponsableTurno = codigoResponsableTurno;
        this.nombreResponsable = nombreResponsable;
        this.apellidoResponsable = apellidoResponsable;
        this.telefonResponsable = telefonResponsable;
        this.codigoArea = codigoArea;
        this.codigoCargo = codigoCargo;
    }

    public int getCodigoResponsableTurno() {
        return codigoResponsableTurno;
    }

    public void setCodigoResponsableTurno(int codigoResponsableTurno) {
        this.codigoResponsableTurno = codigoResponsableTurno;
    }

    public String getNombreResponsable() {
        return nombreResponsable;
    }

    public void setNombreResponsable(String nombreResponsable) {
        this.nombreResponsable = nombreResponsable;
    }

    public String getApellidoResponsable() {
        return apellidoResponsable;
    }

    public void setApellidoResponsable(String apellidoResponsable) {
        this.apellidoResponsable = apellidoResponsable;
    }

    public String getTelefonResponsable() {
        return telefonResponsable;
    }

    public void setTelefonResponsable(String telefonResponsable) {
        this.telefonResponsable = telefonResponsable;
    }

    public int getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(int codigoArea) {
        this.codigoArea = codigoArea;
    }

    public int getCodigoCargo() {
        return codigoCargo;
    }

    public void setCodigoCargo(int codigoCargo) {
        this.codigoCargo = codigoCargo;
    }

    @Override
    public String toString() {
        return "" + codigoResponsableTurno + " - "  + nombreResponsable + " " + apellidoResponsable;
    }
    
    
}
