package Activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.insightapp.R;

public class SolDetailActivity extends AppCompatActivity {
    private TextView tvSolNumber, tvTemperature, tvPressure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvSolNumber = findViewById(R.id.tvSolNumber);
        tvTemperature = findViewById(R.id.tvTemperature);
        tvPressure = findViewById(R.id.tvPressure);


        String solNumber = getIntent().getStringExtra("sol_number");
        String temperature = getIntent().getStringExtra("temperature");
        String pressure = getIntent().getStringExtra("pressure");


        tvSolNumber.setText("Sol n°: " + solNumber);
        tvTemperature.setText("Température: " + temperature);
        tvPressure.setText("Pression: " + pressure);
    }
}