package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.example.t3a3_climent_pablo.pojo.Movimiento

class GlobalPositionDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_position_details)

        // Obtener la cuenta seleccionada, el cliente y los movimientos
        val cuenta = intent.getSerializableExtra("CuentaSeleccionada") as? Cuenta
        val cliente = intent.getSerializableExtra("cliente") as? Cliente
        val movimientos = intent.getSerializableExtra("ListaMovimientos") as? ArrayList<Movimiento>

        Log.d("GlobalPositionDetailsActivity", "Cuenta: $cuenta")
        Log.d("GlobalPositionDetailsActivity", "Cliente: $cliente")
        Log.d("GlobalPositionDetailsActivity", "ListaMovimientos: $movimientos")

        if (cuenta != null) {
            // Cargar el Fragment con la información de la cuenta
            if (savedInstanceState == null) {
                val fragment = GlobalPositionDetailsFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable("cuentaSeleccionada", cuenta)
                        putSerializable("cliente", cliente)
                        putSerializable("ListaMovimientos", movimientos)
                    }
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
            }
        } else {
            // Manejar el caso donde la cuenta no se pasó correctamente
            Log.e("GlobalPositionDetailsActivity", "No se pasó la cuenta correctamente")
        }
    }
}
