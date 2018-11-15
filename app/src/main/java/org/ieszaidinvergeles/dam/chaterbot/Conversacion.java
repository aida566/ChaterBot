package org.ieszaidinvergeles.dam.chaterbot;

public class Conversacion {

    Long id;
    String fecha;

    public Conversacion(Long id, String fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Conversacion() {
    }

    public Conversacion(String fecha) {
        this.fecha = fecha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
