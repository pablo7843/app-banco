package com.example.t3a3_climent_pablo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.t3a3_climent_pablo.pojo.Movimiento

class MovementsAdapter(private var movimientos: List<Movimiento>) :
    RecyclerView.Adapter<MovementsAdapter.MovementViewHolder>() {

    class MovementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDescripcion: TextView = itemView.findViewById(R.id.tvDescripcion)
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val tvImporte: TextView = itemView.findViewById(R.id.tvImporte)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movement, parent, false)
        return MovementViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {
        val movimiento = movimientos[position]
        holder.tvDescripcion.text = movimiento.getDescripcion()
        holder.tvFecha.text = movimiento.getFechaOperacion().toString()
        holder.tvImporte.text = "${movimiento.getImporte()} €"
    }

    override fun getItemCount(): Int = movimientos.size

    fun actualizarMovimientos(nuevosMovimientos: List<Movimiento>) {
        movimientos = nuevosMovimientos
        notifyDataSetChanged()
    }
}
