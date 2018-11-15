package org.ieszaidinvergeles.dam.chaterbot;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class Ayudante extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "chaterbot.db";
    public static final int DATABASE_VERSION = 1;

    public Ayudante(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Contrato.TablaConversacion.SQL_CREATE_CONVERSACION_V1);

        db.execSQL(Contrato.TablaChat.SQL_CREATE_CHAT_V1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int nerVersion) {

        String sql = "drop table if exists " + Contrato.TablaChat.TABLE_NAME;
        db.execSQL(sql);

        sql = "drop table if exists " + Contrato.TablaConversacion.TABLE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }
}
