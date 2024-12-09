package com.example.t3a3_climent_pablo.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3a3_climent_pablo.adapter.MovementsAdapter
import com.example.t3a3_climent_pablo.databinding.FragmentGlobalPositionDetailsBinding
import com.example.t3a3_climent_pablo.pojo.Cliente
import com.example.t3a3_climent_pablo.pojo.Cuenta
import com.example.t3a3_climent_pablo.pojo.Movimiento

class GlobalPositionDetailsFragment : Fragment() {

    private var _binding: FragmentGlobalPositionDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var cuentaSeleccionada: Cuenta
    private lateinit var listaMovimientos: List<Movimiento>
    private var cliente: Cliente? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGlobalPositionDetailsBinding.inflate(inflater, container, false)

        // Recuperar la cuenta desde los argumentos
        arguments?.let {
            cuentaSeleccionada = it.getSerializable("cuentaSeleccionada") as? Cuenta
                ?: throw IllegalStateException("La cuenta seleccionada no se pasó correctamente")
            cliente = it.getSerializable("cliente") as? Cliente
            listaMovimientos = it.getSerializable("ListaMovimientos") as? List<Movimiento> ?: emptyList()
        }

        Log.d("GlobalPositionDetailsFragment", "Cuenta: $cuentaSeleccionada")
        Log.d("GlobalPositionDetailsFragment", "Cliente: $cliente")
        Log.d("GlobalPositionDetailsFragment", "Lista de Movimientos: $listaMovimientos")

        // Configurar RecyclerView para los movimientos
        binding.recyclerViewMovimientos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewMovimientos.adapter = MovementsAdapter(listaMovimientos)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
