package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.adapter.MovementsAdapter
import com.example.t3a3_climent_pablo.databinding.ActivityMovementsBinding
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.example.t3a3_climent_pablo.pojo.Movimiento

class MovementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovementsBinding
    private lateinit var movimientosAdapter: MovementsAdapter
    private lateinit var cuentas: List<Cuenta>
    private lateinit var movimientos: List<Movimiento>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir datos desde el Intent
        cuentas = intent.getSerializableExtra("cuentas") as? List<Cuenta> ?: emptyList()
        movimientos = intent.getSerializableExtra("movimientos") as? List<Movimiento> ?: emptyList()

        // Configurar el Spinner con las cuentas
        val cuentasNombres = cuentas.map { cuenta ->
            "${cuenta.getBanco()}-${cuenta.getNumeroCuenta()}"
        }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cuentasNombres)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMovimientos.adapter = spinnerAdapter

        // Configurar RecyclerView
        binding.recyclerViewMovimientos.layoutManager = LinearLayoutManager(this)
        movimientosAdapter = MovementsAdapter(emptyList()) // Inicialmente vacío
        binding.recyclerViewMovimientos.adapter = movimientosAdapter

        // Listener para actualizar movimientos al seleccionar una cuenta
        binding.spinnerMovimientos.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                val cuentaSeleccionada = cuentas[position]
                val movimientosFiltrados = movimientos.filter { it.getId() == cuentaSeleccionada.getId() }
                movimientosAdapter.actualizarMovimientos(movimientosFiltrados)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
