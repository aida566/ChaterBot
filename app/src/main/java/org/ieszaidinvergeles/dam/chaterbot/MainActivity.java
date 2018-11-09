package org.ieszaidinvergeles.dam.chaterbot;

import android.app.Activity;
import android.content.Context;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

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

    private void chat(final String text) {

        // Pasa a showMessage la respuesta del bot o muestra el tipo de excepción si está se produce

        String response;

        try {
            response = getString(R.string.bot) + " " + botSession.think(text);

        } catch (final Exception e) {

            response = getString(R.string.exception) + " " + e.toString();
        }

        tvTexto.post(showMessage(response));
    }

    private void setEvents() {

        btSend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Muestra el mensaje que escribimos.

                final String text = getString(R.string.you) + " " + etTexto.getText().toString().trim();
                btSend.setEnabled(false);
                etTexto.setText("");
                tvTexto.append(text + "\n");

                //Esta hebra llama a chat, que se encargará de pedir la respuesta del bot.


                /* -----------------------------------------Cambiar------------------------------------------------------*/
                new Thread(){

                    @Override
                    public void run() {

                        chat(text);
                    }

                }.start();
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

    /* -----------------------------------------Cambiar------------------------------------------------------*/
    private Runnable showMessage(final String message) {

        //Devuelve un runnable

        return new Runnable() {

            @Override
            public void run() {
                etTexto.requestFocus(); // Pone el foco del cursor en el editText
                tvTexto.append(message + "\n"); //Añade la interacción nueva
                svScroll.fullScroll(View.FOCUS_DOWN); //Hace scroll hacía abajo para que muestren los últimos mensajes.
                btSend.setEnabled(true);
                hideKeyboard();
            }
        };
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

    
}