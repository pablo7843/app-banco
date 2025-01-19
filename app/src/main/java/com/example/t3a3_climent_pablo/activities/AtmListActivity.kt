package com.example.t3a3_climent_pablo.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.adapter.AtmAdapter
import com.example.t3a3_climent_pablo.adapter.AtmListener
import com.example.t3a3_climent_pablo.databinding.ActivityAtmListBinding
import com.example.t3a3_climent_pablo.entitites.CajeroEntity

class AtmListActivity : AppCompatActivity(), AtmListener {
    private lateinit var context: Context
    private lateinit var atmAdapter: AtmAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var itemDecoration: DividerItemDecoration

    private lateinit var binding: ActivityAtmListBinding
    private lateinit var listaAtms: List<CajeroEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar y configurar el binding
        binding = ActivityAtmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Llamar a la base de datos en un hilo separado
        loadAtmsFromDatabase()

        linearLayoutManager = LinearLayoutManager(this)
        binding.reciclerView.layoutManager = linearLayoutManager
        itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.reciclerView.addItemDecoration(itemDecoration)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadAtmsFromDatabase() {
        // Crear un nuevo hilo para realizar la consulta a la base de datos
        Thread {
            // Realizar la consulta a la base de datos en el hilo secundario
            val atmsFromDb: List<CajeroEntity> = CajeroApplication.database.cajeroDAO().getAllCajeros()

            // Ahora actualizamos el RecyclerView en el hilo principal
            runOnUiThread {
                // Asignar la lista de cajeros obtenida a la variable
                listaAtms = atmsFromDb
                atmAdapter = AtmAdapter(listaAtms, this@AtmListActivity)
                binding.reciclerView.adapter = atmAdapter
            }
        }.start()
    }

    override fun onAtmSeleccionado(cajeroEntity: CajeroEntity) {
        val intent = Intent(this, AtmFormActivity::class.java)
        intent.putExtra("Cajero", cajeroEntity)  // Pasa el objeto completo
        startActivity(intent)
    }
}