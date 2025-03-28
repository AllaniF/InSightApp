package Activity;


import android.os.Bundle;

import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.insightapp.R;

import org.json.JSONObject;

import java.util.List;

import adapter.WeatherAdapter;
import model.Weather;
import service.Callback;
import service.NasaApiService;

public class HomeActivity extends AppCompatActivity {
    private NasaApiService nasaApiService;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        nasaApiService = NasaApiService.getInstance(this);
        listView = findViewById(R.id.listView);


        nasaApiService.getMarsWeatherDetails(
                new Callback<List<Weather>>() {
                    @Override
                    public void onMessage(List<Weather> weatherList) {
                        // Afficher le nombre de sols récupérés
                        int solCount = weatherList.size();
                        Toast.makeText(HomeActivity.this, "Nombre de sols : " + solCount, Toast.LENGTH_SHORT).show();

                        WeatherAdapter adapter = new WeatherAdapter(HomeActivity.this, weatherList);
                        listView.setAdapter(adapter);
                    }
                },
                new Callback<Exception>() {
                    @Override
                    public void onMessage(Exception error) {
                        Toast.makeText(HomeActivity.this, "Erreur : " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );



    }
}