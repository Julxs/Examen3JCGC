package tu.paquete;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText etUsernameSignUp, etPasswordSignUp;
    private Button btnRegistrarse;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etUsernameSignUp = findViewById(R.id.etUsernameSignUp);
        etPasswordSignUp = findViewById(R.id.etPasswordSignUp);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        dbHelper = new DatabaseHelper(this);

        btnRegistrarse.setOnClickListener(v -> {
            String username = etUsernameSignUp.getText().toString().trim();
            String password = etPasswordSignUp.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean exito = dbHelper.insertarJugador(username, password);
            if (exito) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}