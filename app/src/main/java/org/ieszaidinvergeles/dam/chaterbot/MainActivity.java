package org.ieszaidinvergeles.dam.chaterbot;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBot;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotFactory;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotSession;
import org.ieszaidinvergeles.dam.chaterbot.api.ChatterBotType;

//https://github.com/pierredavidbelanger/chatter-bot-api

public class MainActivity extends AppCompatActivity {

    private Button btSend;
    private EditText etTexto;
    private ScrollView svScroll;
    private TextView tvTexto;

    private ChatterBot bot;
    private ChatterBotFactory factory;
    private ChatterBotSession botSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        btSend = findViewById(R.id.btSend);
        etTexto = findViewById(R.id.etTexto);
        svScroll = findViewById(R.id.svScroll);
        tvTexto = findViewById(R.id.tvTexto);

        if(startBot()) {
            setEvents();
        }
    }

    private void setEvents() {

        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                final String text = getString(R.string.you) + " " + etTexto.getText().toString().trim();

                Tarea tarea = new Tarea();
                tarea.execute(new String[]{text});
            }
        });
    }

    private boolean startBot() {

        /* Devuelve true si se crea correctamente el bot
        *  Devuelve false si no ocurre alguna excepción
        */

        boolean result = true;
        String initialMessage;
        factory = new ChatterBotFactory();

        try {

            bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");

            botSession = bot.createSession();

            initialMessage = getString(R.string.messageConnected) + "\n";

        } catch(Exception e) {

            initialMessage = getString(R.string.messageException) + "\n" + getString(R.string.exception) + " " + e.toString();

            result = false;
        }

        tvTexto.setText(initialMessage);

        return result;
    }


    public void hideKeyboard() {

        //Esconde el teclado

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();

        if (view == null) {
            view = new View(this);
        }

        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public class Tarea extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {

            btSend.setEnabled(false);
        }

        @Override
        protected Void doInBackground(String... textH) {

            etTexto.setText("");
            tvTexto.append(textH[0] + "\n");

            // Pasa a showMessage la respuesta del bot o muestra el tipo de excepción si está se produce

            String textoBot;

            try {
                textoBot = getString(R.string.bot) + " " + botSession.think(textH[0]);

            } catch (final Exception e) {

                textoBot = getString(R.string.exception) + " " + e.toString();
            }

            publishProgress(textoBot);

            return null;
        }

        @Override
        protected void onProgressUpdate(String... textoBot) {

            etTexto.requestFocus(); // Pone el foco del cursor en el editText
            tvTexto.append(textoBot[0] + "\n"); //Añade la interacción nueva
            svScroll.fullScroll(View.FOCUS_DOWN); //Hace scroll hacía abajo para que muestren los últimos mensajes.
            btSend.setEnabled(true);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            hideKeyboard();
        }
    }
}