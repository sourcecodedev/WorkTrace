package com.upc.worktrace.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.upc.worktrace.R
import com.upc.worktrace.data.model.entities.Trabajador

class TrabajadorAdapter(
    private val onVerDetalles: (Trabajador) -> Unit,
    private val onEliminar: (Trabajador) -> Unit
) : RecyclerView.Adapter<TrabajadorAdapter.TrabajadorViewHolder>() {

    private val TAG = "TrabajadorAdapter"
    private var trabajadores = listOf<Trabajador>()

    fun actualizarTrabajadores(nuevaLista: List<Trabajador>) {
        Log.d(TAG, "Actualizando lista de trabajadores. Nueva cantidad: ${nuevaLista.size}")
        trabajadores = nuevaLista
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
        Log.v(TAG, "Creando nuevo ViewHolder")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_trabajador, parent, false)
        return TrabajadorViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
        val trabajador = trabajadores[position]
        Log.v(TAG, "Vinculando trabajador en posición $position: ${trabajador.nombres}")
        holder.bind(trabajador)
    }

    override fun getItemCount() = trabajadores.size

    inner class TrabajadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreTrabajador)
        private val btnVerDetalles: ImageButton = itemView.findViewById(R.id.btnVerDetalles)
        private val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminar)

        fun bind(trabajador: Trabajador) {
            tvNombre.text = trabajador.nombres

            btnVerDetalles.setOnClickListener {
                Log.d(TAG, "Click en Ver Detalles para: ${trabajador.nombres}")
                onVerDetalles(trabajador)
            }

            btnEliminar.setOnClickListener {
                Log.d(TAG, "Click en Eliminar para: ${trabajador.nombres}")
                onEliminar(trabajador)
            }
        }
    }
} 