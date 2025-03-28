package service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
import static com.android.volley.DefaultRetryPolicy.DEFAULT_MAX_RETRIES;

import java.util.ArrayList;
import java.util.List;

import model.Weather;

public class NasaApiService {

    private static final String NASA_API_TOKEN = "CYyS337ZbqelE0Dd4bEImhMC1WdxcUfYiP7issOe";
    private static final String NASA_API_BASE_URL = "https://api.nasa.gov/insight_weather/?api_key=" + NASA_API_TOKEN + "&feedtype=json&ver=1.0";

    private static NasaApiService INSTANCE;

    private RequestQueue requestQueue;
    private Context context;

    private NasaApiService(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
        this.context = context;
    }


    public synchronized static NasaApiService getInstance() {
        if (INSTANCE == null) {
            throw new RuntimeException("NasaApiService non initialisé. Appeler getInstance(Context context) en premier.");
        }
        return INSTANCE;
    }

    public synchronized static NasaApiService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new NasaApiService(context);
        }
        return INSTANCE;
    }

    // Méthode pour récupérer la météo sur Mars
//    public void getMarsWeather(Callback<JSONObject> onSuccess, Callback<Exception> onError) {
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
//                Request.Method.GET,
//                NASA_API_BASE_URL,
//                null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Toast.makeText(context, "Données météo récupérées avec succès !", Toast.LENGTH_SHORT).show();
//                        onSuccess.onMessage(response);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(context, "Erreur de récupération : " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                        onError.onMessage(error);
//                    }
//                }
//        );
//
//        // Ajout d'une politique de retry pour gérer les problèmes de réseau
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
//                30 * 1000, // Timeout en ms (30 secondes)
//                DEFAULT_MAX_RETRIES, // Nombre max de tentatives
//                DEFAULT_BACKOFF_MULT // Multiplicateur de délai
//        ));
//
//        this.requestQueue.add(jsonObjectRequest);
//    }

    public void getMarsWeatherDetails(Callback<List<Weather>> onSuccess, Callback<Exception> onError) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                NASA_API_BASE_URL,
                null,
                response -> {
                    try {
                        JSONArray solKeys = response.getJSONArray("sol_keys");
                        List<Weather> weatherList = new ArrayList<>();

                        // Parcours des clés de sols
                        for (int i = 0; i < solKeys.length(); i++) {
                            String sol = solKeys.getString(i);
                            if (response.has(sol)) {
                                JSONObject solData = response.getJSONObject(sol);
                                Weather weather = new Weather(sol, solData);
                                weatherList.add(weather);
                            }
                        }

                        // Appel de la méthode onMessage avec la liste des Weather
                        onSuccess.onMessage(weatherList);
                    } catch (JSONException e) {
                        onError.onMessage(e);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "KO", Toast.LENGTH_SHORT).show();
                        onError.onMessage(error);
                    }
                }
        );

        // Ajout d'une politique de retry pour gérer les problèmes de réseau
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                30 * 1000, // Timeout en ms (30 secondes)
                DEFAULT_MAX_RETRIES, // Nombre max de tentatives
                DEFAULT_BACKOFF_MULT // Multiplicateur de délai
        ));

        this.requestQueue.add(jsonObjectRequest);
    }

}
