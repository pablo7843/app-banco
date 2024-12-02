package com.example.t3a3_climent_pablo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.MovementsAdapter
import com.example.t3a3_climent_pablo.databinding.FragmentMovementsBinding
import com.example.t3a3_climent_pablo.pojo.Movimiento

class MovementsFragment : Fragment() {

    private var _binding: FragmentMovementsBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaMovimientos: ArrayList<Movimiento>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovementsBinding.inflate(inflater, container, false)

        // Obtener lista de movimientos pasada como argumento
        arguments?.let {
            listaMovimientos = it.getSerializable("listaMovimientos") as ArrayList<Movimiento>
        }

        // Configurar RecyclerView
        binding.recyclerViewMovements.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMovements.adapter = MovementsAdapter(listaMovimientos)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
