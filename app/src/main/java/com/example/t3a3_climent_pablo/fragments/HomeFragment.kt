package com.example.t3a3_climent_pablo.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.activities.GlobalPositionActivity
import com.example.t3a3_climent_pablo.activities.MovementsActivity
import com.example.t3a3_climent_pablo.activities.TransferActivity
import com.example.t3a3_climent_pablo.bd.MiBD
import com.example.t3a3_climent_pablo.databinding.FragmentHomeBinding
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.example.t3a3_climent_pablo.pojo.Movimiento

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var cliente: Cliente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cliente = arguments?.getSerializable("Cliente") as Cliente
        Log.d("HomeFragment", "Cliente recibido: $cliente")
        Log.d("HomeFragment", "Lista de cuentas del cliente: ${cliente.obtenerListaCuentas()}")


        // Mensaje de bienvenida
        binding.tvWelcomeMessage.text = "Bienvenido/a ${cliente.getNombre()}"

        // Botón para Posición Global
        binding.btnPosicionGlobal.setOnClickListener {
            val intent = Intent(activity, GlobalPositionActivity::class.java)
            intent.putExtra("Cliente", cliente)
            intent.putExtra("ListaCuentas", ArrayList(cliente.obtenerListaCuentas()))
            intent.putExtra("ListaMovimientos", ArrayList(obtenerMovimientosDesdeBD(cliente)))
            startActivity(intent)
        }

        // Botón para Movimientos
        binding.btnMovimientos.setOnClickListener {
            val intent = Intent(activity, MovementsActivity::class.java)
            intent.putExtra("cuentas", ArrayList(cliente.obtenerListaCuentas()))
            intent.putExtra("movimientos", ArrayList(obtenerMovimientosDesdeBD(cliente)))
            startActivity(intent)
        }

        // Botón para Transferencias
        binding.btnTransferencias.setOnClickListener {
            val intent = Intent(activity, TransferActivity::class.java)
            intent.putExtra("Cliente", cliente)
            startActivity(intent)
        }

        // Botón para Cambiar Clave
        binding.btnCambiarClave.setOnClickListener {
            Toast.makeText(activity, "Función cambiar clave en desarrollo.", Toast.LENGTH_SHORT).show()
        }

        // Botón para Promociones
        binding.btnPromociones.setOnClickListener {
            Toast.makeText(activity, "Función promociones en desarrollo.", Toast.LENGTH_SHORT).show()
        }

        // Botón para Cajeros
        binding.btnCajeros.setOnClickListener {
            Toast.makeText(activity, "Función cajeros en desarrollo.", Toast.LENGTH_SHORT).show()
        }

        // Botón para Salir
        binding.btnSalir.setOnClickListener {
            activity?.finish()
        }
    }

    private fun obtenerMovimientosDesdeBD(cliente: Cliente?): List<Movimiento> {
        val movimientoDAO = MiBD.getInstance(requireContext())?.movimientoDAO
        return cliente?.obtenerListaCuentas()?.flatMap { cuenta ->
            movimientoDAO?.getMovimientos(cuenta)?.filterIsInstance<Movimiento>() ?: emptyList()
        } ?: emptyList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
