package com.example.t3a3_climent_pablo.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.activities.GlobalPositionDetailsActivity
import com.example.t3a3_climent_pablo.adapter.CuentaAdapter
import com.example.t3a3_climent_pablo.databinding.FragmentGlobalPositionBinding
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta

class GlobalPositionFragment : Fragment() {

    private var _binding: FragmentGlobalPositionBinding? = null
    private val binding get() = _binding!!

    private lateinit var listaCuentas: ArrayList<Cuenta>
    private lateinit var cliente: Cliente

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGlobalPositionBinding.inflate(inflater, container, false)

        arguments?.let {
            cliente = it.getSerializable("Cliente") as Cliente // Recuperar cliente correctamente
            listaCuentas = it.getSerializable("ListaCuentas") as ArrayList<Cuenta> // Clave corregida
            Log.d("GlobalPositionFragment", "Lista de cuentas recibida: $listaCuentas")
        }

        // Configurar RecyclerView con listener
        val adapter = CuentaAdapter(listaCuentas, cliente!!) { cuentaSeleccionada ->
            val intent = Intent(requireContext(), GlobalPositionDetailsActivity::class.java)
            intent.putExtra("cuentaSeleccionada", cuentaSeleccionada)
            startActivity(intent)
        }
        binding.recyclerViewAccounts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewAccounts.adapter = adapter

        return binding.root
    }

}