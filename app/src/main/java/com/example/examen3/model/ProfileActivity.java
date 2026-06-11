package tu.paquete;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvUltimaConexion;
    private DatabaseHelper dbHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvUltimaConexion = findViewById(R.id.tvUltimaConexion);
        dbHelper = new DatabaseHelper(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("USERNAME");

        // Mostrar última conexión guardada (antes de actualizarla)
        String ultimaConexion = dbHelper.obtenerUltimaConexion(username);
        tvUltimaConexion.setText("Última conexión: " + ultimaConexion);

        // Actualizar con la conexión actual
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String fechaActual = sdf.format(new Date());
        dbHelper.actualizarUltimaConexion(username, fechaActual);
    }
}