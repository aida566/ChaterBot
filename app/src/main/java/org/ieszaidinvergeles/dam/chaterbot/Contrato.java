package org.ieszaidinvergeles.dam.chaterbot;

import android.provider.BaseColumns;

public class Contrato {

    private Contrato () {
    }
    public static abstract class TablaConversacion implements BaseColumns {

        public static final String TABLE_NAME = "conversacion";
        public static final String COLUMN_NAME_FECHA = "fecha";

        public static final String SQL_CREATE_CONVERSACION_V1 =
                "create table " + TABLE_NAME +
                " (" + _ID + " integer primary key autoincrement, " +
                COLUMN_NAME_FECHA +" DATE);";
    }

    public static abstract class TablaChat implements BaseColumns {

        public static final String TABLE_NAME = "chat";
        public static final String COLUMN_NAME_IDCONVER = "idConversacion";
        public static final String COLUMN_NAME_QUIEN = "quien";
        public static final String COLUMN_NAME_TEXTO = "texto";

        public static final String SQL_CREATE_CHAT_V1 =
                "create table " + TABLE_NAME +
                " (" + _ID + " integer primary key autoincrement, " +
                COLUMN_NAME_IDCONVER + " integer," +
                COLUMN_NAME_QUIEN + "  text," +
                COLUMN_NAME_TEXTO +" text);";
    }
}
