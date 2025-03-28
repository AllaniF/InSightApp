package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.insightapp.R;

import org.json.JSONObject;

import service.Callback;
import service.NasaApiService;

public class HomeActivity extends AppCompatActivity {
    private NasaApiService nasaApiService;
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


        nasaApiService = new NasaApiService(this);


        nasaApiService.getMarsWeather(this, new Callback<JSONObject>() {
            @Override
            public void onMessage(JSONObject data) {
                Toast.makeText(HomeActivity.this, "Température reçue !", Toast.LENGTH_SHORT).show();
                // Ici, tu peux traiter les données JSON reçues
            }
        });
    }
}