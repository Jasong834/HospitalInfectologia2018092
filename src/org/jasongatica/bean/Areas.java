
package org.jasongatica.bean;

public class Areas {
    
    private int codigoArea;
    private String nombreArea;

    public Areas() {
    }

    public Areas(int codigoArea, String nombreArea) {
        this.codigoArea = codigoArea;
        this.nombreArea = nombreArea;
    }

    public int getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(int codigoArea) {
        this.codigoArea = codigoArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    @Override
    public String toString() {
        return ""+codigoArea + "-" + nombreArea;
    }


    
}
