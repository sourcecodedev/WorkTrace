package com.upc.worktrace

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.upc.worktrace.viewmodel.WorkerViewModel

class AdminAddWorkerActivity : AppCompatActivity() {

    private lateinit var viewModel: WorkerViewModel
    

    private lateinit var etId: TextInputLayout
    private lateinit var etNombres: TextInputLayout
    private lateinit var etPuesto: TextInputLayout
    private lateinit var etJefeInmediato: TextInputLayout
    private lateinit var etDireccion: TextInputLayout
    private lateinit var etTelefono: TextInputLayout
    private lateinit var spinnerTipoContrato: TextInputLayout
    private lateinit var spinnerDistritoTrabajo: TextInputLayout
    private lateinit var btnGuardar: MaterialButton
    private lateinit var btnCancelar: MaterialButton
    private lateinit var progressBar: ProgressBar


    private val camposRequeridos = mutableListOf<Pair<TextInputLayout, TextInputEditText>>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_worker)


        viewModel = ViewModelProvider(this)[WorkerViewModel::class.java]

        initializeViews()
        setupToolbar()
        setupCamposRequeridos()
        setupSpinners()
        setupObservers()
        setupListeners()
    }

    private fun initializeViews() {
        etId = findViewById(R.id.etId)
        etNombres = findViewById(R.id.etNombres)
        etPuesto = findViewById(R.id.etPuesto)
        etJefeInmediato = findViewById(R.id.etJefeInmediato)
        etDireccion = findViewById(R.id.etDireccion)
        etTelefono = findViewById(R.id.etTelefono)
        spinnerTipoContrato = findViewById(R.id.spinnerTipoContrato)
        spinnerDistritoTrabajo = findViewById(R.id.spinnerDistritoTrabajo)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupToolbar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        findViewById<View>(R.id.btnBack).setOnClickListener { onBackPressed() }
        findViewById<android.widget.TextView>(R.id.toolbarTitle).text = "Agregar Trabajador"
    }

    private fun setupCamposRequeridos() {
        camposRequeridos.addAll(listOf(
            Pair(etId, etId.editText as TextInputEditText),
            Pair(etNombres, etNombres.editText as TextInputEditText),
            Pair(etPuesto, etPuesto.editText as TextInputEditText),
            Pair(etJefeInmediato, etJefeInmediato.editText as TextInputEditText),
            Pair(etDireccion, etDireccion.editText as TextInputEditText),
            Pair(etTelefono, etTelefono.editText as TextInputEditText)
        ))
    }

    private fun setupSpinners() {

        val tiposContrato = listOf("Tiempo Completo", "Medio Tiempo", "Por Horas")
        val adapterTipoContrato = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tiposContrato)
        (spinnerTipoContrato.editText as? AutoCompleteTextView)?.setAdapter(adapterTipoContrato)
        (spinnerTipoContrato.editText as? AutoCompleteTextView)?.setText(tiposContrato[0], false)

        // Configurar Spinner Distrito Trabajo
        val distritos = listOf("San Isidro", "Miraflores", "San Borja", "Surco", "La Molina")
        val adapterDistrito = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, distritos)
        (spinnerDistritoTrabajo.editText as? AutoCompleteTextView)?.setAdapter(adapterDistrito)
        (spinnerDistritoTrabajo.editText as? AutoCompleteTextView)?.setText(distritos[0], false)
    }

    private fun setupObservers() {
        viewModel.cargando.observe(this) { estaCargando ->
            progressBar.visibility = if (estaCargando) View.VISIBLE else View.GONE
            btnGuardar.isEnabled = !estaCargando
            btnCancelar.isEnabled = !estaCargando
        }

        viewModel.resultadoTrabajador.observe(this) { resultado ->
            if (resultado.success) {
                Toast.makeText(this, "Trabajador registrado exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, resultado.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        btnGuardar.setOnClickListener {
            if (validarCampos()) {
                registrarTrabajador()
            }
        }

        btnCancelar.setOnClickListener {
            finish()
        }
    }

    private fun validarCampos(): Boolean {
        var isValid = true
        

        camposRequeridos.forEach { (layout, editText) ->
            if (editText.text.toString().trim().isEmpty()) {
                layout.error = "Campo requerido"
                isValid = false
            } else {
                layout.error = null
            }
        }


        if ((spinnerTipoContrato.editText as? AutoCompleteTextView)?.text.toString().isEmpty()) {
            spinnerTipoContrato.error = "Seleccione un tipo de contrato"
            isValid = false
        } else {
            spinnerTipoContrato.error = null
        }

        if ((spinnerDistritoTrabajo.editText as? AutoCompleteTextView)?.text.toString().isEmpty()) {
            spinnerDistritoTrabajo.error = "Seleccione un distrito"
            isValid = false
        } else {
            spinnerDistritoTrabajo.error = null
        }


        val telefono = etTelefono.editText?.text.toString()
        if (telefono.isNotEmpty() && !telefono.matches(Regex("^[0-9]{9}$"))) {
            etTelefono.error = "Ingrese un número válido de 9 dígitos"
            isValid = false
        }


        val id = etId.editText?.text.toString()
        if (id.isNotEmpty() && !id.matches(Regex("^[0-9]+$"))) {
            etId.error = "Ingrese un ID válido (solo números)"
            isValid = false
        }

        return isValid
    }

    private fun registrarTrabajador() {
        try {
            val id = etId.editText?.text.toString().toInt()
            val nombres = etNombres.editText?.text.toString()
            val puesto = etPuesto.editText?.text.toString()
            val jefeInmediato = etJefeInmediato.editText?.text.toString()
            val direccion = etDireccion.editText?.text.toString()
            val telefono = etTelefono.editText?.text.toString()
            val tipoContrato = (spinnerTipoContrato.editText as AutoCompleteTextView).text.toString()
            val distrito = (spinnerDistritoTrabajo.editText as AutoCompleteTextView).text.toString()

            viewModel.registrarTrabajador(

                nombres = nombres,
                puesto = puesto,
                jefeInmediato = jefeInmediato,
                idTipoContrato = obtenerIdTipoContrato(tipoContrato),
                direccion = direccion,
                telefono = telefono,
                idDistritoTrabajo = obtenerIdDistrito(distrito),
                password = id.toString() // Usando el ID como contraseña por defecto
            )
        } catch (e: Exception) {
            Toast.makeText(this, 
                "Error al procesar los datos: ${e.message}", 
                Toast.LENGTH_LONG).show()
        }
    }

    private fun obtenerIdTipoContrato(tipo: String): Int {
        return when (tipo) {
            "Tiempo Completo" -> 1
            "Medio Tiempo" -> 2
            "Por Horas" -> 3
            else -> 1
        }
    }

    private fun obtenerIdDistrito(distrito: String): Int {
        return when (distrito) {
            "San Isidro" -> 1
            "Miraflores" -> 2
            "San Borja" -> 3
            "Surco" -> 4
            "La Molina" -> 5
            else -> 1
        }
    }
} 