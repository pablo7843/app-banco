package com.example.t3a3_climent_pablo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.adapter.CuentaAdapter
import com.example.t3a3_climent_pablo.databinding.FragmentGlobalPositionBinding
import com.example.t3a3_climent_pablo.pojo.Cuenta

class GlobalPositionFragment : Fragment() {

    private var _binding: FragmentGlobalPositionBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaCuentas: ArrayList<Cuenta>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGlobalPositionBinding.inflate(inflater, container, false)

        // Obtener la lista de cuentas pasada como argumento
        arguments?.let {
            listaCuentas = it.getSerializable("listaCuentas") as ArrayList<Cuenta>
        }

        // Configurar RecyclerView para mostrar las cuentas
        binding.recyclerViewAccounts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAccounts.adapter = CuentaAdapter(listaCuentas)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

