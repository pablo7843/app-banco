package com.example.t3a3_climent_pablo
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.t3a3_climent_pablo.bd.MiBancoOperacional
import com.example.t3a3_climent_pablo.databinding.ActivityLoginBinding
import com.example.t3a3_climent_pablo.pojo.Cliente


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón de Entrar
        binding.buttonLogin.setOnClickListener {

            val mbo: MiBancoOperacional? = MiBancoOperacional.getInstance(this)

            var cliente = Cliente()
            cliente.setNif(binding.etDni.text.toString())
            cliente.setClaveSeguridad(binding.etPassword.text.toString())

            val clienteLogueado = mbo?.login(cliente) ?: -1
            if(clienteLogueado == -1) {
                Toast.makeText(this, "El cliente no existe en la BD", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("Cliente", clienteLogueado)
                startActivity(intent)
            }




            val dni = binding.etDni.text.toString()
            val password = binding.etPassword.text.toString()

            // Validamos los campos
            if (dni.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Pasamos el DNI a la MainActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("dni", dni)
                startActivity(intent)
            }
        }

        // Botón de Salir
        binding.buttonSalir.setOnClickListener {
            finish()  // Cierra la app
        }
    }
}