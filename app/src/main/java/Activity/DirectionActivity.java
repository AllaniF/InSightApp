package Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.insightapp.R;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class DirectionActivity extends AppCompatActivity {
    private Button btnStart, btnStop, btnDirectLeft, btnDirectFront, btnDirectRight;

    private Socket socket;
    private OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_direction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnDirectLeft = findViewById(R.id.btnDirectLeft);
        btnDirectFront = findViewById(R.id.btnDirectFront);
        btnDirectRight = findViewById(R.id.btnDirectRight);

        btnStart.setOnClickListener(v -> sendCommand("START\n"));
        btnStop.setOnClickListener(v -> sendCommand("STOP\n"));
        btnDirectLeft.setOnClickListener(v -> sendCommand("DIRECT_LEFT\n"));
        btnDirectFront.setOnClickListener(v -> sendCommand("DIRECT_FRONT\n"));
        btnDirectRight.setOnClickListener(v -> sendCommand("DIRECT_RIGHT\n"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connexion à l'IP et au port du robot dans un thread séparé
        new Thread(() -> {
            try {
                socket = new Socket("10.0.2.2", 7777); // Remplacer par l'IP et le port corrects
                outputStream = socket.getOutputStream();
                runOnUiThread(() -> Toast.makeText(DirectionActivity.this, "Connecté au robot", Toast.LENGTH_SHORT).show());
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(DirectionActivity.this, "Erreur de connexion", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        new Thread(() -> {
            try {
                if (socket != null) {
                    socket.close();
                    runOnUiThread(() -> Toast.makeText(DirectionActivity.this, "Déconnecté du robot", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendCommand(String command) {
        if (outputStream != null) {
            new Thread(() -> {
                try {
                    outputStream.write(command.getBytes());
                    outputStream.flush();
                    runOnUiThread(() -> Toast.makeText(DirectionActivity.this, "Commande envoyée: " + command, Toast.LENGTH_SHORT).show());
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(DirectionActivity.this, "Erreur d'envoi de commande", Toast.LENGTH_SHORT).show());
                }
            }).start();
        } else {
            runOnUiThread(() -> Toast.makeText(DirectionActivity.this, "Pas de connexion disponible", Toast.LENGTH_SHORT).show());
        }
    }
}
