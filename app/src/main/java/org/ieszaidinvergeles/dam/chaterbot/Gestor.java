package org.ieszaidinvergeles.dam.chaterbot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Gestor {

    private Ayudante adb;
    private SQLiteDatabase bd;

    public Gestor(Context c) {

        this(c, true);
    }

    public Gestor(Context c, boolean write) {

        adb = new Ayudante(c);

        if(write){
            bd = adb.getWritableDatabase();

        }else if(!write){
            bd = adb.getReadableDatabase();

        }
    }

    public void close() {

        adb.close();

    }

    public long insertConversacion(Conversacion con) {

        ContentValues valores = new ContentValues();

        valores.put(Contrato.TablaConversacion.COLUMN_NAME_FECHA, con.getFecha());

        long id = bd.insert(Contrato.TablaConversacion.TABLE_NAME, null, valores);

        return id;
    }
    public long insertChat(Chat chat) {

        ContentValues valores = new ContentValues();

        valores.put(Contrato.TablaChat.COLUMN_NAME_IDCONVER, chat.getIdConver());
        valores.put(Contrato.TablaChat.COLUMN_NAME_QUIEN, chat.getQuien());
        valores.put(Contrato.TablaChat.COLUMN_NAME_TEXTO, chat.getText());

        long id = bd.insert(Contrato.TablaChat.TABLE_NAME, null, valores);

        return id;
    }

    public int deleteConversacion(Conversacion con) {

        String condicion = Contrato.TablaConversacion._ID + " = ?";

        String[] argumentos = { con.getId() + "" };

        int cuenta = bd.delete(Contrato.TablaConversacion.TABLE_NAME, condicion,argumentos);

        return cuenta;
    }

    public Cursor getCursorConversacion() {

        Cursor cursor = bd.query(
                Contrato.TablaConversacion.TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    public Conversacion getRowConversacion(Cursor c) {

        Conversacion con = new Conversacion();
        con.setId(c.getLong(0));
        con.setFecha(c.getString(1));

        return con;

    }

    public Conversacion get(String fecha) {

        String[] proyeccion = {
                Contrato.TablaConversacion._ID,
                Contrato.TablaConversacion.COLUMN_NAME_FECHA};

        String where = Contrato.TablaConversacion.COLUMN_NAME_FECHA+ " = ?";

        String[] parametros = new String[] { fecha + "" };

        String groupby = null;
        String having = null;
        String orderby = null;

        Cursor c = bd.query(Contrato.TablaConversacion.TABLE_NAME, proyeccion,
                where, parametros, groupby, having, orderby);

        if(c.moveToFirst()){

            Conversacion con = getRowConversacion(c);

            c.close();

            return con;

        }else{
            return null;
        }
    }
}
