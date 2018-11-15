package org.ieszaidinvergeles.dam.chaterbot;

import android.content.ContentValues;
import android.content.Context;
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

    public int deleteConversacion(Conversacion con) {

        String condicion = Contrato.TablaConversacion._ID + " = ?";

        String[] argumentos = { con.getId() + "" };

        int cuenta = bd.delete(Contrato.TablaConversacion.TABLE_NAME, condicion,argumentos);

        return cuenta;
    }


}
