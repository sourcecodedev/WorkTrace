package com.upc.worktrace

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.upc.worktrace.ui.adapter.CustomSpinnerAdapter
import com.upc.worktrace.viewmodel.WorkerViewModel
import com.upc.worktrace.viewmodel.DistritoViewModel
import com.upc.worktrace.viewmodel.TipoContratoViewModel

class AdminAddWorkerActivity : AppCompatActivity() {
    private lateinit var viewModel: WorkerViewModel
    private lateinit var distritoViewModel: DistritoViewModel
    private lateinit var tipoContratoViewModel: TipoContratoViewModel
    
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
    private var selectedDistritoId: Int? = null
    private var selectedTipoContratoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_worker)
        
        initializeViewModels()
        initializeViews()
        setupToolbar()
        setupCamposRequeridos()
        setupSpinners()
        setupObservers()
        setupListeners()
    }

    private fun initializeViewModels() {
        viewModel = ViewModelProvider(this)[WorkerViewModel::class.java]
        distritoViewModel = ViewModelProvider(this)[DistritoViewModel::class.java]
        tipoContratoViewModel = ViewModelProvider(this)[TipoContratoViewModel::class.java]
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

        etId.editText?.apply {
            setText("9999")
            isEnabled = false
        }
        etId.isEnabled = false
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
            Pair(etNombres, etNombres.editText as TextInputEditText),
            Pair(etPuesto, etPuesto.editText as TextInputEditText),
            Pair(etJefeInmediato, etJefeInmediato.editText as TextInputEditText),
            Pair(etDireccion, etDireccion.editText as TextInputEditText),
            Pair(etTelefono, etTelefono.editText as TextInputEditText)
        ))
    }

    private fun setupSpinners() {
        setupTipoContratoSpinner()
        setupDistritoTrabajoSpinner()
    }

    private fun setupTipoContratoSpinner() {
        val autoComplete = spinnerTipoContrato.editText as? AutoCompleteTextView ?: return
        autoComplete.isEnabled = false
        
        tipoContratoViewModel.tiposContratoSpinner.observe(this) { tiposContrato ->
            if (tiposContrato.isNotEmpty()) {
                val adapter = CustomSpinnerAdapter(this, tiposContrato)
                autoComplete.setAdapter(adapter)
                autoComplete.setText(tiposContrato[0].nombre, false)
                selectedTipoContratoId = tiposContrato[0].id
                autoComplete.isEnabled = true
            }

            autoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                val spinnerAdapter = parent.adapter as? CustomSpinnerAdapter<Int>
                spinnerAdapter?.getSelectedItemId(position)?.let { id ->
                    selectedTipoContratoId = id
                }
            }
        }

        tipoContratoViewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(this, "Error al cargar tipos de contrato: $it", Toast.LENGTH_LONG).show()
                autoComplete.isEnabled = true
            }
        }

        tipoContratoViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            autoComplete.isEnabled = !isLoading
        }
    }

    private fun setupDistritoTrabajoSpinner() {
        distritoViewModel.distritosSpinner.observe(this) { distritos ->
            val adapter = CustomSpinnerAdapter(this, distritos)
            (spinnerDistritoTrabajo.editText as? AutoCompleteTextView)?.let { autoComplete ->
                autoComplete.setAdapter(adapter)
                
                if (distritos.isNotEmpty()) {
                    val primerDistrito = distritos[0]
                    selectedDistritoId = primerDistrito.id
                    autoComplete.setText(primerDistrito.nombre, false)
                }

                autoComplete.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                    val distrito = distritos.getOrNull(position)
                    if (distrito != null) {
                        selectedDistritoId = distrito.id
                    } else if (distritos.isNotEmpty()) {
                        selectedDistritoId = distritos[0].id
                        autoComplete.setText(distritos[0].nombre, false)
                    }
                }

                autoComplete.setOnDismissListener {
                    if (selectedDistritoId == null || selectedDistritoId == 0) {
                        if (distritos.isNotEmpty()) {
                            selectedDistritoId = distritos[0].id
                            autoComplete.setText(distritos[0].nombre, false)
                        }
                    }
                }
            }
        }

        distritoViewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(this, "Error al cargar distritos: $it", Toast.LENGTH_LONG).show()
            }
        }

        distritoViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupObservers() {
        viewModel.cargando.observe(this) { estaCargando ->
            progressBar.visibility = if (estaCargando) View.VISIBLE else View.GONE
            btnGuardar.isEnabled = !estaCargando
        }

        viewModel.resultadoTrabajador.observe(this) { resultado ->
            if (resultado.success) {
                Toast.makeText(
                    this,
                    resultado.message ?: "Trabajador registrado correctamente",
                    Toast.LENGTH_LONG
                ).show()
                setResult(RESULT_OK)
                finish()
            } else {
                Toast.makeText(
                    this,
                    resultado.message ?: "Error al registrar trabajador",
                    Toast.LENGTH_LONG
                ).show()
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
            if (selectedDistritoId == null || selectedDistritoId == 0) {
                throw Exception("Debe seleccionar un distrito válido")
            }

            val nombres = etNombres.editText?.text.toString()
            val puesto = etPuesto.editText?.text.toString()
            val jefeInmediato = etJefeInmediato.editText?.text.toString()
            val direccion = etDireccion.editText?.text.toString()
            val telefono = etTelefono.editText?.text.toString()

            val idTipoContrato = selectedTipoContratoId
                ?: throw Exception("Debe seleccionar un tipo de contrato")

            viewModel.registrarTrabajador(
                nombres = nombres,
                puesto = puesto,
                jefeInmediato = jefeInmediato,
                idTipoContrato = idTipoContrato,
                direccion = direccion,
                telefono = telefono,
                idDistritoTrabajo = selectedDistritoId!!,
                estado = 1 // Estado activo por defecto
            )
        } catch (e: Exception) {
            Toast.makeText(this, 
                "Error al procesar los datos: ${e.message}", 
                Toast.LENGTH_LONG).show()
        }
    }
} 