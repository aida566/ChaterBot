package org.ieszaidinvergeles.dam.chaterbot;

public class Chat {

    int id;
    long idConver;
    String quien;
    String text;

    public Chat(long idConver, String quien, String text) {
        this.idConver = idConver;
        this.quien = quien;
        this.text = text;
    }

    public Chat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getIdConver() {
        return idConver;
    }

    public void setIdConver(int idConver) {
        this.idConver = idConver;
    }

    public String getQuien() {
        return quien;
    }

    public void setQuien(String quien) {
        this.quien = quien;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
