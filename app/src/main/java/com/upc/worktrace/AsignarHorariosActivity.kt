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
    private lateinit var cbMiercoles: CheckBox
    private lateinit var cbJueves: CheckBox
    private lateinit var cbViernes: CheckBox
    private lateinit var cbSabado: CheckBox
    private lateinit var btnHoraEntrada: MaterialButton
    private lateinit var btnHoraSalida: MaterialButton
    private lateinit var btnGuardar: MaterialButton
    private lateinit var btnCancelar: MaterialButton


    private val trabajadores = listOf("Yhimy Feria", "Ana Torres")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asignar_horarios)


        setupToolbar(true, "Asignar Horario")


        etNombreTrabajador = findViewById(R.id.etNombreTrabajador)
        cbLunes = findViewById(R.id.cbLunes)
        cbMartes = findViewById(R.id.cbMartes)
        cbMiercoles = findViewById(R.id.cbMiercoles)
        cbJueves = findViewById(R.id.cbJueves)
        cbViernes = findViewById(R.id.cbViernes)
        cbSabado = findViewById(R.id.cbSabado)

        btnHoraEntrada = findViewById(R.id.btnHoraEntrada)
        btnHoraSalida = findViewById(R.id.btnHoraSalida)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)


        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, trabajadores)
        etNombreTrabajador.setAdapter(adapter)


        btnHoraEntrada.setOnClickListener {
            mostrarSelectorHora(true)
        }

        btnHoraSalida.setOnClickListener {
            mostrarSelectorHora(false)
        }


        btnGuardar.setOnClickListener {
            guardarHorario()
        }


        btnCancelar.setOnClickListener {

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
        val tieneMiercoles = cbMiercoles.isChecked
        val tieneJueves = cbJueves.isChecked
        val tieneViernes = cbViernes.isChecked
        val tieneSabado = cbSabado.isChecked
        val horaEntrada = btnHoraEntrada.text.toString()
        val horaSalida = btnHoraSalida.text.toString()


        if (nombreTrabajador.isEmpty()) {
            Toast.makeText(this, "Debe seleccionar un trabajador", Toast.LENGTH_SHORT).show()
            return
        }

        if (!tieneLunes && !tieneMartes && !tieneMiercoles && !tieneJueves && !tieneViernes && !tieneSabado) {
            Toast.makeText(this, "Debe seleccionar al menos un día de trabajo", Toast.LENGTH_SHORT).show()
            return
        }

        if (horaEntrada.isEmpty() || horaSalida.isEmpty()) {
            Toast.makeText(this, "Debe seleccionar horas válidas", Toast.LENGTH_SHORT).show()
            return
        }


        val idHorarioAsignacion = 1


        val diasSeleccionados = mutableListOf<Int>()
        if (tieneLunes) diasSeleccionados.add(1)
        if (tieneMartes) diasSeleccionados.add(2)
        if (tieneMiercoles) diasSeleccionados.add(3)
        if (tieneJueves) diasSeleccionados.add(4)
        if (tieneViernes) diasSeleccionados.add(5)
        if (tieneSabado) diasSeleccionados.add(6)


        for (dia in diasSeleccionados) {

        }

        Toast.makeText(this, "Horario asignado correctamente", Toast.LENGTH_SHORT).show()
        finish()
    }


} 