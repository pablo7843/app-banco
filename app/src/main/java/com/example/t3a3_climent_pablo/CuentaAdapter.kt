package com.example.t3a3_climent_pablo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t3a3_climent_pablo.R
import com.example.t3a3_climent_pablo.pojo.Cuenta

class CuentaAdapter(private val cuentas: List<Cuenta>) :
    RecyclerView.Adapter<CuentaAdapter.CuentaViewHolder>() {

    class CuentaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val numeroCuenta: TextView = itemView.findViewById(R.id.textNumeroCuenta)
        val saldo: TextView = itemView.findViewById(R.id.textSaldo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cuenta, parent, false)
        return CuentaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CuentaViewHolder, position: Int) {
        val cuenta = cuentas[position]
        holder.numeroCuenta.text = cuenta.getNumeroCuenta() ?: "Cuenta no disponible"
        holder.saldo.text = "Saldo: ${cuenta.getSaldoActual()} €"
    }

    override fun getItemCount(): Int = cuentas.size
}
