package org.jasongatica.bean;


public class Turno {
    private int codigoTurno;
    private String fechaTurno;
    private String fecharCita;
    private float valorCita;
    private int codigoResponsableTurno;
    private int codigoPaciente;
    private int codigoMedicoEspecialidad;

    public Turno() {
    }

    public Turno(int codigoTurno, String fechaTurno, String fecharCita, float valorCita, int codigoResponsableTurno, int codigoPaciente, int codigoMedicoEspecialidad) {
        this.codigoTurno = codigoTurno;
        this.fechaTurno = fechaTurno;
        this.fecharCita = fecharCita;
        this.valorCita = valorCita;
        this.codigoResponsableTurno = codigoResponsableTurno;
        this.codigoPaciente = codigoPaciente;
        this.codigoMedicoEspecialidad = codigoMedicoEspecialidad;
    }

    public int getCodigoTurno() {
        return codigoTurno;
    }

    public void setCodigoTurno(int codigoTurno) {
        this.codigoTurno = codigoTurno;
    }

    public String getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(String fechaTurno) {
        this.fechaTurno = fechaTurno;
    }

    public String getFecharCita() {
        return fecharCita;
    }

    public void setFecharCita(String fecharCita) {
        this.fecharCita = fecharCita;
    }

    public float getValorCita() {
        return valorCita;
    }

    public void setValorCita(float valorCita) {
        this.valorCita = valorCita;
    }

    public int getCodigoResponsableTurno() {
        return codigoResponsableTurno;
    }

    public void setCodigoResponsableTurno(int codigoResponsableTurno) {
        this.codigoResponsableTurno = codigoResponsableTurno;
    }

    public int getCodigoPaciente() {
        return codigoPaciente;
    }

    public void setCodigoPaciente(int codigoPaciente) {
        this.codigoPaciente = codigoPaciente;
    }

    public int getCodigoMedicoEspecialidad() {
        return codigoMedicoEspecialidad;
    }

    public void setCodigoMedicoEspecialidad(int codigoMedicoEspecialidad) {
        this.codigoMedicoEspecialidad = codigoMedicoEspecialidad;
    }

    @Override
    public String toString() {
        return " " +codigoTurno + "-" + "Fecha:" +fechaTurno ;
    }
}
