package com.example.t3a3_climent_pablo.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.adapter.MovementsAdapter
import com.example.t3a3_climent_pablo.databinding.FragmentMovementsBinding
import com.example.t3a3_climent_pablo.pojo.Movimiento
import com.google.android.material.bottomnavigation.BottomNavigationView

class MovementsFragment : Fragment() {

    private var _binding: FragmentMovementsBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaMovimientos: ArrayList<Movimiento>
    private lateinit var movimientosOriginales: ArrayList<Movimiento>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovementsBinding.inflate(inflater, container, false)

        // Obtener lista de movimientos pasada como argumento
        arguments?.let {
            listaMovimientos = it.getSerializable("listaMovimientos") as ArrayList<Movimiento>
            movimientosOriginales = ArrayList(listaMovimientos) // Guardar copia original
            Log.d("MovementsFragment", "Lista de movimientos recibida: $listaMovimientos")
        }

        // Configurar RecyclerView
        binding.recyclerViewMovements.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMovements.adapter = MovementsAdapter(listaMovimientos)

        // Configurar el Bottom Navigation
        val bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_all -> filtrarMovimientos(-1) // Mostrar todos
                R.id.nav_tipo0 -> filtrarMovimientos(0)  // Filtrar tipo 0
                R.id.nav_tipo1 -> filtrarMovimientos(1)  // Filtrar tipo 1
                R.id.nav_tipo2 -> filtrarMovimientos(2)  // Filtrar tipo 2
            }
            true
        }

        return binding.root
    }

    private fun filtrarMovimientos(tipo: Int) {
        val movimientosFiltrados = if (tipo == -1) {
            movimientosOriginales // Mostrar todos
        } else {
            movimientosOriginales.filter { it.getTipo() == tipo } as ArrayList<Movimiento>
        }

        // Actualizar el adaptador con los movimientos filtrados
        binding.recyclerViewMovements.adapter = MovementsAdapter(movimientosFiltrados)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
