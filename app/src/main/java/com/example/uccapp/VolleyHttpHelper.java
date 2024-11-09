package com.example.uccapp;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/* ------------------------------------------------------------------------

    Clase que permite realizar diversos tipos de peticiones HTTP
    hacia un servidor utilizando formato JSON y recibir respuestas.

    Métodos: GET, PUT, DELETE, POST

 ------------------------------------------------------------------------ */

public class VolleyHttpHelper {

    private static VolleyHttpHelper instance;
    private RequestQueue requestQueue;

    // Singleton Pattern
    private VolleyHttpHelper(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleyHttpHelper getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyHttpHelper(context);
        }
        return instance;
    }

    // Generic Method for all types of requests (POST, GET, PUT, DELETE)
    public void makeRequest(int method, String url, JSONObject requestBody,
                            final VolleyResponseListener listener) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                method, url, requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json"); // Add the JSON content type header
                return headers;
            }
        };

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest);
    }


    // GET Request
    public void get(String url, final VolleyResponseListener listener) {
        makeRequest(Request.Method.GET, url, null, listener);
    }

    // POST Request
    public void post(String url, JSONObject requestBody, final VolleyResponseListener listener) {
        makeRequest(Request.Method.POST, url, requestBody, listener);
    }

    // PUT Request
    public void put(String url, JSONObject requestBody, final VolleyResponseListener listener) {
        makeRequest(Request.Method.PUT, url, requestBody, listener);
    }

    // DELETE Request
    public void delete(String url, final VolleyResponseListener listener) {
        makeRequest(Request.Method.DELETE, url, null, listener);
    }

    // Response Listener Interface to handle callbacks
    public interface VolleyResponseListener {
        void onResponse(JSONObject response);
        void onError(VolleyError error);
    }
}
