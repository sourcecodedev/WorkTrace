package com.upc.worktrace

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class HistorialAsistenciaActivity : BaseActivity() {
    
    private lateinit var etUsuario: AutoCompleteTextView
    private lateinit var btnRangoFechas: MaterialButton
    private lateinit var rvAsistencias: RecyclerView
    private lateinit var tvFechaInicio: TextView
    private lateinit var tvFechaFin: TextView
    private lateinit var btnAtras: MaterialButton
    

    private val trabajadores = listOf("Yhimy Feria", "Ana Torres")
    

    private val asistencias = listOf(
        Asistencia("15/04", "08:32", true),
        Asistencia("15/04", "17:29", false)
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_asistencia)
        

        setupToolbar(true, "Historial de Asistencia")
        

        etUsuario = findViewById(R.id.etUsuario)
        btnRangoFechas = findViewById(R.id.btnRangoFechas)
        rvAsistencias = findViewById(R.id.rvAsistencias)
        tvFechaInicio = findViewById(R.id.tvFechaInicio)
        tvFechaFin = findViewById(R.id.tvFechaFin)
        btnAtras = findViewById(R.id.btnAtras)
        

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, trabajadores)
        etUsuario.setAdapter(adapter)
        

        btnRangoFechas.setOnClickListener {
            mostrarSelectorFechas()
        }
        

        btnAtras.setOnClickListener {

            finish()
        }
        

        val asistenciasAdapter = AsistenciasAdapter(asistencias)
        rvAsistencias.layoutManager = LinearLayoutManager(this)
        rvAsistencias.adapter = asistenciasAdapter
    }
    
    private fun mostrarSelectorFechas() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Seleccionar rango de fechas")
            .build()
            
        dateRangePicker.addOnPositiveButtonClickListener { selection ->

            val startDate = selection.first
            val endDate = selection.second
            

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val startDateString = dateFormat.format(Date(startDate))
            val endDateString = dateFormat.format(Date(endDate))
            

            btnRangoFechas.text = "$startDateString - $endDateString"
            

        }
        
        dateRangePicker.show(supportFragmentManager, "DATE_RANGE_PICKER")
    }
    

    data class Asistencia(val fecha: String, val hora: String, val esEntrada: Boolean)
    

    inner class AsistenciasAdapter(
        private val asistencias: List<Asistencia>
    ) : RecyclerView.Adapter<AsistenciasAdapter.AsistenciaViewHolder>() {
        
        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): AsistenciaViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_asistencia, parent, false)
            return AsistenciaViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: AsistenciaViewHolder, position: Int) {
            val asistencia = asistencias[position]
            holder.bind(asistencia)
        }
        
        override fun getItemCount() = asistencias.size
        
        inner class AsistenciaViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
            private val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
            private val tvHora: TextView = itemView.findViewById(R.id.tvHora)
            private val ivEstado: android.widget.ImageView = itemView.findViewById(R.id.ivEstado)
            
            fun bind(asistencia: Asistencia) {
                tvFecha.text = asistencia.fecha
                tvHora.text = asistencia.hora
                

                ivEstado.setImageResource(
                    if (asistencia.esEntrada) R.drawable.ic_entrada else R.drawable.ic_salida
                )
            }
        }
    }
} 