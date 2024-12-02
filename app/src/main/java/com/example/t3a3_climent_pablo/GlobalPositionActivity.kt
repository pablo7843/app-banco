package com.example.t3a3_climent_pablo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.adapter.CuentaAdapter
import com.example.t3a3_climent_pablo.databinding.ActivityGlobalPositionBinding
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta

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

        // Configurar el RecyclerView con el adaptador
        val adapter = CuentaAdapter(listaCuentas)
        binding.recyclerViewAccounts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewAccounts.adapter = adapter
    }
}

