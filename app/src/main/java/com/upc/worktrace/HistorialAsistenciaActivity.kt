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
    
    // Lista simulada de trabajadores
    private val trabajadores = listOf("Yhimy Feria", "Ana Torres")
    
    // Datos simulados de asistencia
    private val asistencias = listOf(
        Asistencia("15/04", "08:32", true),
        Asistencia("15/04", "17:29", false)
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_asistencia)
        
        // Configurar la barra superior
        setupToolbar(true, "Historial de Asistencia")
        
        // Inicializar vistas
        etUsuario = findViewById(R.id.etUsuario)
        btnRangoFechas = findViewById(R.id.btnRangoFechas)
        rvAsistencias = findViewById(R.id.rvAsistencias)
        tvFechaInicio = findViewById(R.id.tvFechaInicio)
        tvFechaFin = findViewById(R.id.tvFechaFin)
        btnAtras = findViewById(R.id.btnAtras)
        
        // Configurar el AutoCompleteTextView con la lista de trabajadores
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, trabajadores)
        etUsuario.setAdapter(adapter)
        
        // Configurar el botón para seleccionar rango de fechas
        btnRangoFechas.setOnClickListener {
            mostrarSelectorFechas()
        }
        
        // Configurar el botón atrás
        btnAtras.setOnClickListener {
            // Simplemente cerramos la actividad para volver a la anterior
            finish()
        }
        
        // Configurar el RecyclerView
        val asistenciasAdapter = AsistenciasAdapter(asistencias)
        rvAsistencias.layoutManager = LinearLayoutManager(this)
        rvAsistencias.adapter = asistenciasAdapter
    }
    
    private fun mostrarSelectorFechas() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Seleccionar rango de fechas")
            .build()
            
        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            // Se seleccionó un rango de fechas
            val startDate = selection.first
            val endDate = selection.second
            
            // Formatear las fechas
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val startDateString = dateFormat.format(Date(startDate))
            val endDateString = dateFormat.format(Date(endDate))
            
            // Actualizar el botón con el rango seleccionado
            btnRangoFechas.text = "$startDateString - $endDateString"
            
            // También podríamos actualizar la lista de asistencias según el rango seleccionado
            // Por ahora, simplemente mantenemos los datos simulados
        }
        
        dateRangePicker.show(supportFragmentManager, "DATE_RANGE_PICKER")
    }
    
    // Clase de datos para representar una asistencia
    data class Asistencia(val fecha: String, val hora: String, val esEntrada: Boolean)
    
    // Adapter para la lista de asistencias
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
                
                // Mostrar icono según si es entrada o salida
                ivEstado.setImageResource(
                    if (asistencia.esEntrada) R.drawable.ic_entrada else R.drawable.ic_salida
                )
            }
        }
    }
} 