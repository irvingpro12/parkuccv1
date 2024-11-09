package com.example.uccapp;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import android.os.AsyncTask;
import android.widget.Toast;
// other imports...

/* ------------------------------------------------------------------------

    Clase de ejemplo para poder obtener información de la base
    datos Mysql utilizando la API REST desarrollada en PHP.

    Al final vienen varios métodos que

 ------------------------------------------------------------------------ */

public class MainActivity extends AppCompatActivity {

    Button login_btn;
    EditText correo_input;
    EditText contrasena_input;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        login_btn = findViewById(R.id.loginButton);
        correo_input = findViewById(R.id.usernameEditText);
        contrasena_input = findViewById(R.id.passwordEditText);

        // Crear nuevo objeto para manejar las peticiones HTTP

        VolleyHttpHelper volleyHttpHelper = VolleyHttpHelper.getInstance(this);

        // Evento para click de botón "Iniciar Sesión"

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Verificar que los campos no estén vacios

                String correo = correo_input.getText().toString();
                String contrasena = contrasena_input.getText().toString();

                if (correo.isEmpty() || contrasena.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Te hace falta llenar un campo", Toast.LENGTH_SHORT).show();

                } else {

                    // Si no están vacios

                    // La información que vamos a mandar como el correo y contraseña en formato JSON
                    // EJEMPLO: { "correo": "elvis@ucc.com", "contrasena": "1234" }

                    JSONObject json = new JSONObject();
                    try {
                        json.put("correo", correo); // correo que introdujo el usuario
                        json.put("contrasena", contrasena); // contrasena que introdujo el usuario
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    // Indicar el metodo (GET, POST, DELETE, PUT), pasar la URL del servidor y JSON

                    volleyHttpHelper.post("http://157.230.232.203/validarUsuario", json, new VolleyHttpHelper.VolleyResponseListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            // Hacer algo en caso de obtener respuesta
                            Toast.makeText(getApplicationContext(), "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                            // Vamos a poner la respuesta como String en la pantalla

                            try {

                                // Extraer el valor del parámetro "edad"
                                int status = response.getInt("status");

                                // Paso 3: Usar el valor en una condición `if`
                                if (status == 200) {
                                    Intent i = new Intent(MainActivity.this, Inicio.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(getApplicationContext(), "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
                            }

                            // La respuesta saldrá algo como así { "status": 200, "message": "User validated successfully." }

                            // Ustedes vean como manejar esa información

                            // Les recomiendo

                        }

                        @Override
                        public void onError(VolleyError error) {
                            // Hacer algo en caso de error


                        }

                    });

                    /* -------------------------- EJEMPLOS DE TODOS LOS MÉTODOS --------------------------------------------------------

                    // Ejemplo GET

                    volleyHttpHelper.get("http://157.230.232.203/", new VolleyHttpHelper.VolleyResponseListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("GET Response", response.toString());
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.e("GET Error", error.toString());
                        }
                    });

                    // Ejemplo POST

                    volleyHttpHelper.post("http://157.230.232.203/", json, new VolleyHttpHelper.VolleyResponseListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("POST Response", response.toString());
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.e("POST Error", error.toString());
                        }
                    });

                    // Ejemplo PUT

                    volleyHttpHelper.put("http://157.230.232.203/", json, new VolleyHttpHelper.VolleyResponseListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("PUT Response", response.toString());
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.e("PUT Error", error.toString());
                        }
                    });

                    // Ejemplo DELETE

                    volleyHttpHelper.delete("http://157.230.232.203/", new VolleyHttpHelper.VolleyResponseListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("DELETE Response", response.toString());
                        }

                        @Override
                        public void onError(VolleyError error) {
                            Log.e("DELETE Error", error.toString());
                        }
                    });

                     */

                }
            }
        });
    }
}

