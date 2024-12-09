package com.example.t3a3_climent_pablo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.t3a3_climent_pablo.bd.MiBD
import com.example.t3a3_climent_pablo.databinding.ActivityMainBinding
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.example.t3a3_climent_pablo.pojo.Movimiento

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var cliente: Cliente

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Obtener el cliente pasado por el intent
        cliente = intent.getSerializableExtra("Cliente") as Cliente
        Log.d("MainActivity", "Cliente: $cliente")

        // Mensaje de bienvenida
        binding.tvWelcomeMessage.text = "Bienvenido/a ${cliente.getNombre()}"

        // Cargar las cuentas del cliente desde la base de datos
        if (cliente != null) {
            cliente.setListaDeCuentas(cargarCuentasDesdeBD(cliente))
        }

        // Botón para Posición Global
        binding.btnPosicionGlobal.setOnClickListener {
            val intent = Intent(this, GlobalPositionActivity::class.java)
            // Pasar el cliente y las cuentas
            intent.putExtra("Cliente", cliente)
            intent.putExtra("ListaCuentas", ArrayList(cliente.obtenerListaCuentas())) // Asegúrate que obtenerListaCuentas() devuelve una lista serializable
            intent.putExtra("ListaMovimientos", ArrayList(obtenerMovimientosDesdeBD(cliente)))
            startActivity(intent)
        }


        // Botón para Movimientos
        binding.btnMovimientos.setOnClickListener {
            val intent = Intent(this, MovementsActivity::class.java)
            // Convertimos la lista de cuentas y movimientos en Serializable
            intent.putExtra("cuentas", ArrayList(cliente?.obtenerListaCuentas()))
            intent.putExtra("movimientos", ArrayList(obtenerMovimientosDesdeBD(cliente)))

            startActivity(intent)
        }

        // Botón para Transferencias
        binding.btnTransferencias.setOnClickListener {
            val intent = Intent(this, TransferActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        // Botón para Cambiar Clave
        binding.btnCambiarClave.setOnClickListener {
            Toast.makeText(this, "Función cambiar clave en desarrollo.", Toast.LENGTH_SHORT).show()
        }

        // Botón para Promociones
        binding.btnPromociones.setOnClickListener {
            Toast.makeText(this, "Función promociones en desarrollo.", Toast.LENGTH_SHORT).show()
        }

        // Botón para Cajeros
        binding.btnCajeros.setOnClickListener {
            Toast.makeText(this, "Función cajeros en desarrollo.", Toast.LENGTH_SHORT).show()
        }

        // Botón para Salir
        binding.btnSalir.setOnClickListener {
            finish()
        }

        // Botón flotante
        binding.fabBank.setOnClickListener {
            Toast.makeText(this, "Función Banco (Botón flotante) en desarrollo.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun obtenerMovimientosDesdeBD(cliente: Cliente?): List<Movimiento> {
        val movimientoDAO = MiBD.getInstance(this)?.movimientoDAO
        return cliente?.obtenerListaCuentas()?.flatMap { cuenta ->
            movimientoDAO?.getMovimientos(cuenta)?.filterIsInstance<Movimiento>() ?: emptyList()
        } ?: emptyList()
    }


    private fun cargarCuentasDesdeBD(cliente: Cliente): ArrayList<Cuenta> {
        val cuentaDAO = MiBD.getInstance(this)?.cuentaDAO
        return (cuentaDAO?.getCuentas(cliente) as? ArrayList<Cuenta>) ?: arrayListOf()
    }

}
