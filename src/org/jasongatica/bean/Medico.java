package org.jasongatica.bean;




public class Medico {
    private int codigoMedico;
    private int licenciaMedica;
    private String nombres;
    private String apellidos;
    private String horaEntrada;
    private String horaSalida;
    private String sexo;

    public Medico(int codigoMedico, int licenciaMedica, String nombres, String apellidos, String horaEntrada, String horaSalida, String sexo) {
        this.codigoMedico = codigoMedico;
        this.licenciaMedica = licenciaMedica;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.sexo = sexo;
    }

    public Medico() {
    }

    public int getCodigoMedico() {
        return codigoMedico;
    }

    public void setCodigoMedico(int codigoMedico) {
        this.codigoMedico = codigoMedico;
    }

    public int getLicenciaMedica() {
        return licenciaMedica;
    }

    public void setLicenciaMedica(int licenciaMedica) {
        this.licenciaMedica = licenciaMedica;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public  String getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "" + codigoMedico + "-" + nombres +" "+apellidos;
    }

   
    
    
}
