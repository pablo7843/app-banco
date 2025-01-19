package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.databinding.ActivityAtmFormBinding
import com.example.t3a3_climent_pablo.entitites.CajeroEntity

class AtmFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAtmFormBinding
    private lateinit var cajeroEntity: CajeroEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAtmFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la Toolbar como ActionBar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Comprobar si se pasa un objeto CajeroEntity desde el Intent
        cajeroEntity = intent.getSerializableExtra("Cajero") as CajeroEntity?
            ?: CajeroEntity(direccion = "", latitud = 0.0, longitud = 0.0, zoom = "") // Si es null, crear un nuevo CajeroEntity vacío

        // Inicializar los campos con los valores del CajeroEntity
        binding.textoDireccion.setText(cajeroEntity.direccion)
        binding.textoLatitud.setText(cajeroEntity.latitud.toString())
        binding.textoLongitud.setText(cajeroEntity.longitud.toString())

        // Configurar el botón "Guardar"
        binding.botonGuardar.setOnClickListener {
            guardarNuevoCajero()  // Llamada a la función guardar
        }

        // Configurar el botón "Cancelar"
        binding.botonCancelar.setOnClickListener {
            cancelarOperacion()  // Llamada a la función cancelar
        }
    }

    // Inflar el menú de opciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.atm_form_menu, menu)
        return true
    }

    // Manejar las opciones del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.modify -> {
                // Obtener los nuevos valores ingresados por el usuario
                val nuevaDireccion = binding.textoDireccion.text.toString()
                val nuevaLatitud = binding.textoLatitud.text.toString().toDoubleOrNull()
                val nuevaLongitud = binding.textoLongitud.text.toString().toDoubleOrNull()

                if (nuevaLatitud != null && nuevaLongitud != null) {
                    // Actualizar los valores en el objeto CajeroEntity
                    cajeroEntity.direccion = nuevaDireccion
                    cajeroEntity.latitud = nuevaLatitud
                    cajeroEntity.longitud = nuevaLongitud

                    // Usar un Thread para actualizar la base de datos en segundo plano
                    Thread {
                        // Realizar la actualización en el hilo de fondo
                        CajeroApplication.database.cajeroDAO().updateCajero(cajeroEntity)

                        // Volver al hilo principal para mostrar el mensaje
                        runOnUiThread {
                            Toast.makeText(this, "Cajero modificado correctamente", Toast.LENGTH_SHORT).show()
                            finish() // Cerrar la actividad después de modificar
                        }
                    }.start()
                } else {
                    Toast.makeText(this, "Por favor ingresa valores válidos para la latitud y longitud.", Toast.LENGTH_SHORT).show()
                }
                true
            }
            R.id.delete -> {
                // Usar un Thread para eliminar el cajero de la base de datos
                Thread {
                    // Eliminar el cajero en el hilo de fondo
                    CajeroApplication.database.cajeroDAO().deleteCajero(cajeroEntity)

                    // Volver al hilo principal para mostrar el mensaje
                    runOnUiThread {
                        Toast.makeText(this, "Cajero borrado correctamente", Toast.LENGTH_SHORT).show()
                        finish() // Cerrar la actividad después de borrar
                    }
                }.start()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Función para guardar un nuevo CajeroEntity
    private fun guardarNuevoCajero() {
        val nuevaDireccion = binding.textoDireccion.text.toString()
        val nuevaLatitud = binding.textoLatitud.text.toString().toDoubleOrNull()
        val nuevaLongitud = binding.textoLongitud.text.toString().toDoubleOrNull()

        if (nuevaLatitud != null && nuevaLongitud != null && nuevaDireccion.isNotEmpty()) {
            // Crear un nuevo CajeroEntity
            val nuevoCajero = CajeroEntity(direccion = nuevaDireccion, latitud = nuevaLatitud, longitud = nuevaLongitud, zoom = "")

            // Usar un Thread para insertar el nuevo cajero en la base de datos
            Thread {
                CajeroApplication.database.cajeroDAO().addCajero(nuevoCajero)

                // Volver al hilo principal para mostrar el mensaje
                runOnUiThread {
                    Toast.makeText(this, "Nuevo cajero creado correctamente", Toast.LENGTH_SHORT).show()
                    finish() // Cerrar la actividad después de guardar
                }
            }.start()
        } else {
            Toast.makeText(this, "Por favor ingresa todos los datos", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para cancelar la operación y limpiar los campos
    private fun cancelarOperacion() {
        binding.textoDireccion.text?.clear()
        binding.textoLatitud.text?.clear()
        binding.textoLongitud.text?.clear()
    }
}