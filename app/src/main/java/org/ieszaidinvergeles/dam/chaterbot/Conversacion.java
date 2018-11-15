package org.ieszaidinvergeles.dam.chaterbot;

public class Conversacion {

    String id;
    String fecha;

    public Conversacion(String id, String fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Conversacion() {
    }

    public Conversacion(String fecha) {
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
