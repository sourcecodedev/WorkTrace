package com.upc.worktrace

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class AsignarHorariosActivity : BaseActivity() {
    
    private lateinit var etNombreTrabajador: AutoCompleteTextView
    private lateinit var cbLunes: CheckBox
    private lateinit var cbMartes: CheckBox
    private lateinit var btnHoraEntrada: MaterialButton
    private lateinit var btnHoraSalida: MaterialButton
    private lateinit var btnGuardar: MaterialButton
    private lateinit var btnCancelar: MaterialButton
    
    // Lista simulada de trabajadores
    private val trabajadores = listOf("Yhimy Feria", "Ana Torres")
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignar_horarios)
        
        // Configurar la barra superior
        setupToolbar(true, "Asignar Horario")
        
        // Inicializar vistas
        etNombreTrabajador = findViewById(R.id.etNombreTrabajador)
        cbLunes = findViewById(R.id.cbLunes)
        cbMartes = findViewById(R.id.cbMartes)
        btnHoraEntrada = findViewById(R.id.btnHoraEntrada)
        btnHoraSalida = findViewById(R.id.btnHoraSalida)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)
        
        // Configurar el AutoCompleteTextView con la lista de trabajadores
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, trabajadores)
        etNombreTrabajador.setAdapter(adapter)
        
        // Configurar los botones de hora
        btnHoraEntrada.setOnClickListener {
            mostrarSelectorHora(true)
        }
        
        btnHoraSalida.setOnClickListener {
            mostrarSelectorHora(false)
        }
        
        // Configurar el botón de guardar
        btnGuardar.setOnClickListener {
            guardarHorario()
        }
        
        // Configurar el botón de cancelar
        btnCancelar.setOnClickListener {
            // Simplemente cerramos la actividad para volver a la anterior
            finish()
        }
    }
    
    private fun mostrarSelectorHora(esHoraEntrada: Boolean) {
        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(8)
            .setMinute(if (esHoraEntrada) 30 else 0)
            .setTitleText(if (esHoraEntrada) "Seleccionar hora de entrada" else "Seleccionar hora de salida")
            .build()
            
        picker.addOnPositiveButtonClickListener {
            val hora = picker.hour
            val minuto = picker.minute
            val horaFormateada = String.format("%02d:%02d", hora, minuto)
            
            if (esHoraEntrada) {
                btnHoraEntrada.text = horaFormateada
            } else {
                btnHoraSalida.text = horaFormateada
            }
        }
        
        picker.show(supportFragmentManager, "TIME_PICKER")
    }
    
    private fun guardarHorario() {
        val nombreTrabajador = etNombreTrabajador.text.toString().trim()
        val tieneLunes = cbLunes.isChecked
        val tieneMartes = cbMartes.isChecked
        val horaEntrada = btnHoraEntrada.text.toString()
        val horaSalida = btnHoraSalida.text.toString()
        
        // Validación básica
        if (nombreTrabajador.isEmpty()) {
            Toast.makeText(this, "Debe seleccionar un trabajador", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (!tieneLunes && !tieneMartes) {
            Toast.makeText(this, "Debe seleccionar al menos un día de trabajo", Toast.LENGTH_SHORT).show()
            return
        }
        
        if (horaEntrada == "08:30" && horaSalida == "17:30") {
            // Valores por defecto, no han sido modificados
        }
        
        // Aquí iría la lógica para guardar el horario en una base de datos
        // Por ahora, simplemente mostramos un mensaje y cerramos la actividad
        Toast.makeText(this, "Horario asignado correctamente", Toast.LENGTH_SHORT).show()
        finish()
    }
} 