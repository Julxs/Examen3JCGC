package tu.paquete

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var etUsername: EditText? = null
    private var etPassword: EditText? = null
    private var btnIniciarSesion: Button? = null
    private var btnRegistrar: Button? = null
    private var dbHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_)

        etUsername = findViewById<EditText?>(R.id.etUsername)
        etPassword = findViewById<EditText?>(R.id.etPassword)
        btnIniciarSesion = findViewById<Button?>(R.id.btnIniciarSesion)
        btnRegistrar = findViewById<Button?>(R.id.btnRegistrar)
        dbHelper = DatabaseHelper(this)

        btnIniciarSesion!!.setOnClickListener(View.OnClickListener { v: View? ->
            val username = etUsername!!.getText().toString().trim { it <= ' ' }
            val password = etPassword!!.getText().toString().trim { it <= ' ' }
            if (dbHelper!!.validarCredenciales(username, password)) {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                intent.putExtra("USERNAME", username)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        })

        btnRegistrar!!.setOnClickListener(View.OnClickListener { v: View? ->
            val username = etUsername!!.getText().toString().trim { it <= ' ' }
            if (!username.isEmpty() && dbHelper!!.existeUsuario(username)) {
                Toast.makeText(this, "Ya está registrado", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(Intent(this@MainActivity, SignUpActivity::class.java))
            }
        })