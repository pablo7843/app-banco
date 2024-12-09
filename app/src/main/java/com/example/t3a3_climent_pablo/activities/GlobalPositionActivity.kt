package com.example.t3a3_climent_pablo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.adapter.CuentaAdapter
import com.example.t3a3_climent_pablo.databinding.ActivityGlobalPositionBinding
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.example.t3a3_climent_pablo.pojo.Movimiento

class GlobalPositionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlobalPositionBinding
    private lateinit var listaCuentas: ArrayList<Cuenta>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlobalPositionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtener el cliente y las cuentas pasadas por el intent
        val cliente = intent.getSerializableExtra("Cliente") as? Cliente
        listaCuentas = intent.getSerializableExtra("ListaCuentas") as? ArrayList<Cuenta> ?: arrayListOf()
        val movimientos = intent.getSerializableExtra("ListaMovimientos") as? ArrayList<Movimiento> ?: arrayListOf()

        //Logs
        Log.d("GlobalPositionActivity", "Cliente: $cliente")
        Log.d("GlobalPositionActivity", "Lista de cuentas: $listaCuentas")

        // Configurar el RecyclerView con el adaptador
        val adapter = CuentaAdapter(listaCuentas, cliente!!) { cuenta ->
            // Manejar el clic en una cuenta
            val intent = Intent(this, GlobalPositionDetailsActivity::class.java)
            intent.putExtra("Cliente", cliente) // Pasar el cliente
            intent.putExtra("CuentaSeleccionada", cuenta) // Pasar la cuenta seleccionada
            intent.putExtra("ListaMovimientos", movimientos) // Pasar la lista de movimientos
            startActivity(intent)
        }
        binding.recyclerViewAccounts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewAccounts.adapter = adapter
    }
}