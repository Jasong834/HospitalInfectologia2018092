package org.jasongatica.bean;


public class ContactoUrgencia {
    private int codigoContactoUrgencia; 
    private String nombre; 
    private String apellido; 
    private String numeroContacto;
    private int codigoPaciente;

    public ContactoUrgencia(int codigoContactoUrgencia, String nombre, String apellido, String numeroContacto, int codigoPaciente) {
        this.codigoContactoUrgencia = codigoContactoUrgencia;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.codigoPaciente = codigoPaciente;
    }

    public ContactoUrgencia() {
    }

    public int getCodigoContactoUrgencia() {
        return codigoContactoUrgencia;
    }

    public void setCodigoContactoUrgencia(int codigoContactoUrgencia) {
        this.codigoContactoUrgencia = codigoContactoUrgencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public int getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(int codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    @Override
    public String toString() {
        return "" + codigoContactoUrgencia;
    }
  


    
    
    
}
