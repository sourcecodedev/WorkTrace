package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import com.upc.worktrace.viewmodel.WorkerViewModel
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView
import com.upc.worktrace.data.model.entities.Trabajador
import java.util.Date

class EditarTrabajadorActivity : AppCompatActivity() {
    private val TAG = "EditarTrabajadorActivity"

    private lateinit var toolbar: Toolbar
    private lateinit var tilNombre: TextInputLayout
    private lateinit var tilPuesto: TextInputLayout
    private lateinit var tilJefeInmediato: TextInputLayout
    private lateinit var tilDireccion: TextInputLayout
    private lateinit var tilTelefono: TextInputLayout
    private lateinit var spinnerTipoContrato: TextInputLayout
    private lateinit var spinnerDistritoTrabajo: TextInputLayout
    
    private lateinit var etNombre: TextInputEditText
    private lateinit var etPuesto: TextInputEditText
    private lateinit var etJefeInmediato: TextInputEditText
    private lateinit var etDireccion: TextInputEditText
    private lateinit var etTelefono: TextInputEditText
    
    private lateinit var progressIndicator: CircularProgressIndicator
    private val viewModel: WorkerViewModel by viewModels()
    
    private var idTrabajador: Int = 0

    private lateinit var tvIdTrabajador: TextView
    private lateinit var btnGuardar: MaterialButton
    private lateinit var btnCancelar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_editar_trabajador)
            
            idTrabajador = intent.getIntExtra("idTrabajador", 0)
            if (idTrabajador == 0) {
                mostrarError("ID de trabajador no válido")
                finish()
                return
            }
            
            setupToolbar()
            initializeViews()
            setupListeners()
            setupObservers()
            cargarDatosTrabajador()
            cargarSpinners()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error en onCreate", e)
            mostrarError("Error al inicializar la pantalla")
            finish()
        }
    }

    private fun initializeViews() {
        try {
            Log.d(TAG, "Inicializando vistas")
            
            toolbar = findViewById(R.id.toolbar)
            progressIndicator = findViewById(R.id.progress_indicator)
            
            tilNombre = findViewById(R.id.til_nombre)
            tilPuesto = findViewById(R.id.til_puesto)
            tilJefeInmediato = findViewById(R.id.til_jefe_inmediato)
            tilDireccion = findViewById(R.id.til_direccion)
            tilTelefono = findViewById(R.id.til_telefono)
            spinnerTipoContrato = findViewById(R.id.spinner_tipo_contrato)
            spinnerDistritoTrabajo = findViewById(R.id.spinner_distrito_trabajo)
            
            etNombre = findViewById(R.id.et_nombre)
            etPuesto = findViewById(R.id.et_puesto)
            etJefeInmediato = findViewById(R.id.et_jefe_inmediato)
            etDireccion = findViewById(R.id.et_direccion)
            etTelefono = findViewById(R.id.et_telefono)

            tvIdTrabajador = findViewById(R.id.tv_id_trabajador)
            btnGuardar = findViewById(R.id.btn_guardar)
            btnCancelar = findViewById(R.id.btn_cancelar)

            tvIdTrabajador.text = "ID: $idTrabajador"
        } catch (e: Exception) {
            Log.e(TAG, "Error al inicializar vistas", e)
            throw e
        }
    }

    private fun setupObservers() {
        viewModel.trabajadores.observe(this) { trabajadores ->
            if (!trabajadores.isNullOrEmpty()) {
                val trabajador = trabajadores.firstOrNull { it.idTrabajador == idTrabajador }
                trabajador?.let {
                    etNombre.setText(it.nombres)
                    etPuesto.setText(it.puesto)
                    etJefeInmediato.setText(it.jefeInmediato)
                    etDireccion.setText(it.direccion)
                    etTelefono.setText(it.telefono)
                    
                    (spinnerTipoContrato.editText as? AutoCompleteTextView)?.setText(it.idTipoContrato.toString(), false)
                    (spinnerDistritoTrabajo.editText as? AutoCompleteTextView)?.setText(it.idDistritoTrabajo.toString(), false)
                }
            }
        }

        viewModel.resultadoTrabajador.observe(this) { resultado ->
            if (resultado.success) {
                mostrarMensaje("Cambios guardados correctamente")
                setResult(RESULT_OK)
                finish()
            } else {
                mostrarError(resultado.message ?: "Error al guardar los cambios")
            }
        }

        viewModel.cargando.observe(this) { isLoading ->
            progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun cargarDatosTrabajador() {
        viewModel.obtenerTrabajadorPorId(idTrabajador)
    }

    private fun cargarSpinners() {
        val tiposContrato = listOf("Tiempo completo", "Medio tiempo", "Por proyecto")
        val distritos = listOf("San Isidro", "Miraflores", "San Borja", "La Molina")

        val adapterTipoContrato = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tiposContrato)
        val adapterDistrito = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, distritos)

        (spinnerTipoContrato.editText as? AutoCompleteTextView)?.setAdapter(adapterTipoContrato)
        (spinnerDistritoTrabajo.editText as? AutoCompleteTextView)?.setAdapter(adapterDistrito)
    }

    private fun guardarCambios() {
        try {
            val nombre = etNombre.text.toString()
            val puesto = etPuesto.text.toString()
            val jefeInmediato = etJefeInmediato.text.toString()
            val direccion = etDireccion.text.toString()
            val telefono = etTelefono.text.toString()
            val tipoContrato = (spinnerTipoContrato.editText as? AutoCompleteTextView)?.text.toString()
            val distrito = (spinnerDistritoTrabajo.editText as? AutoCompleteTextView)?.text.toString()

            if (nombre.isBlank() || puesto.isBlank() || jefeInmediato.isBlank() ||
                direccion.isBlank() || telefono.isBlank() || tipoContrato.isBlank() || distrito.isBlank()) {
                mostrarError("Todos los campos son obligatorios")
                return
            }

            viewModel.registrarTrabajador(
                nombres = nombre,
                puesto = puesto,
                jefeInmediato = jefeInmediato,
                idTipoContrato = tipoContrato.toIntOrNull() ?: 1,
                direccion = direccion,
                telefono = telefono,
                idDistritoTrabajo = distrito.toIntOrNull() ?: 1,
                estado = 1
            )
        } catch (e: Exception) {
            mostrarError("Error al guardar los cambios")
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            title = "Editar Trabajador"
        }
    }

    private fun setupListeners() {
        btnGuardar.setOnClickListener {
            guardarCambios()
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun mostrarError(mensaje: String) {
        Snackbar.make(findViewById(android.R.id.content), mensaje, Snackbar.LENGTH_LONG)
            .setBackgroundTint(getColor(android.R.color.holo_red_dark))
            .setTextColor(getColor(android.R.color.white))
            .show()
    }

    private fun mostrarMensaje(mensaje: String) {
        Snackbar.make(findViewById(android.R.id.content), mensaje, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(getColor(android.R.color.holo_green_dark))
            .setTextColor(getColor(android.R.color.white))
            .show()
    }
}



