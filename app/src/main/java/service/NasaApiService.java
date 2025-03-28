package service;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NasaApiService {
    private static final String NASA_API_TOKEN = "CYyS337ZbqelE0Dd4bEImhMC1WdxcUfYiP7issOe";
    private static final String NASA_API_BASE_URL = "https://api.nasa.gov/insight_weather/?api_key=" + NASA_API_TOKEN + "&feedtype=json&ver=1.0";

    private RequestQueue queue;

    public NasaApiService(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    public void getMarsWeather(Context context, Callback<JSONObject> callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, NASA_API_BASE_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(context, "Données météo récupérées avec succès !", Toast.LENGTH_SHORT).show();
                        callback.onMessage(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Erreur de récupération : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);
    }






}
