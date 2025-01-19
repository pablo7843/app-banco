package com.example.t3a3_climent_pablo.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.databinding.ActivityAtmManagementBinding
import com.example.t3a3_climent_pablo.entitites.CajeroEntity
import com.example.t3a3_climent_pablo.pojo.Cliente

class AtmManagementActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAtmManagementBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAtmManagementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar cliente desde los argumentos
        val cliente = intent.getSerializableExtra("Cliente") as? Cliente

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Thread {
            val cajerosEntityLists : List<CajeroEntity> = listOf(
                CajeroEntity(direccion = "Carrer del Clariano, 1, 46021 Valencia, Valencia, España",
                    latitud = 39.47600769999999, longitud = -0.3524475000000393, zoom = ""),
                CajeroEntity( direccion = "Avinguda del Cardenal Benlloch, 65, 46021 València, Valencia,España", latitud = 39.4710366, longitud =  -0.3547525000000178, zoom =  ""),
                CajeroEntity(direccion = "Av. del Port, 237, 46011 València, Valencia, España",
                    latitud = 39.46161999999999, longitud = -0.3376299999999901, zoom = ""),
                CajeroEntity(direccion = "Carrer del Batxiller, 6, 46010 València, Valencia, España",
                    latitud = 39.4826729, longitud =  -0.3639118999999482, zoom = ""),
                CajeroEntity(direccion = "Av. del Regne de València, 2, 46005 València, Valencia, España",
                    latitud = 39.4647669, longitud = -0.3732760000000326, zoom =  ""))

            CajeroApplication.database.cajeroDAO().insertAll(cajerosEntityLists)
        }.start()



        binding.botonLista.setOnClickListener {
            val intent = Intent(this, AtmListActivity::class.java)
            startActivity(intent)
        }

        binding.botonAnadir.setOnClickListener {
            val intent = Intent(this, AtmFormActivity::class.java)
            startActivity(intent)
        }

    }

}