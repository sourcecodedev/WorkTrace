package com.upc.worktrace

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.upc.worktrace.ui.adapter.CustomSpinnerAdapter
import com.upc.worktrace.ui.adapter.SpinnerItem
import com.upc.worktrace.viewmodel.WorkerViewModel
import com.upc.worktrace.viewmodel.DistritoViewModel
import com.upc.worktrace.viewmodel.TipoContratoViewModel

class AdminAddWorkerActivity : AppCompatActivity() {

    private val TAG = "AdminAddWorkerActivity"

    private lateinit var viewModel: WorkerViewModel
    private lateinit var distritoViewModel: DistritoViewModel
    private lateinit var tipoContratoViewModel: TipoContratoViewModel
    
    // Vistas
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

    // Lista de campos para validación
    private val camposRequeridos = mutableListOf<Pair<TextInputLayout, TextInputEditText>>()
    
    private var selectedDistritoId: Int? = null
    private var selectedTipoContratoId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_worker)
        
        Log.d(TAG, "Iniciando AdminAddWorkerActivity")
        
        initializeViewModels()
        initializeViews()
        setupToolbar()
        setupCamposRequeridos()
        setupSpinners()
        setupObservers()
        setupListeners()
    }

    private fun initializeViewModels() {
        Log.d(TAG, "Inicializando ViewModels")
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
        Log.d(TAG, "Configurando spinners")
        setupTipoContratoSpinner()
        setupDistritoTrabajoSpinner()
    }

    private fun setupTipoContratoSpinner() {
        Log.d(TAG, "Configurando spinner de tipo de contrato")
        
        // Asegurarse de que el AutoCompleteTextView esté configurado correctamente
        val autoComplete = spinnerTipoContrato.editText as? AutoCompleteTextView
        if (autoComplete == null) {
            Log.e(TAG, "Error: spinnerTipoContrato no es un AutoCompleteTextView")
            return
        }

        // Deshabilitar el spinner mientras se cargan los datos
        autoComplete.isEnabled = false
        
        tipoContratoViewModel.tiposContratoSpinner.observe(this) { tiposContrato ->
            Log.d(TAG, "Recibidos tipos de contrato: $tiposContrato")
            
            if (tiposContrato.isNotEmpty()) {
                val adapter = CustomSpinnerAdapter(this, tiposContrato)
                autoComplete.setAdapter(adapter)
                
                // Seleccionar el primer item por defecto
                autoComplete.setText(tiposContrato[0].nombre, false)
                selectedTipoContratoId = tiposContrato[0].id
                
                // Habilitar el spinner
                autoComplete.isEnabled = true
            }

            autoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                val spinnerAdapter = parent.adapter as? CustomSpinnerAdapter<Int>
                spinnerAdapter?.getSelectedItemId(position)?.let { id ->
                    selectedTipoContratoId = id
                    val nombreTipoContrato = tiposContrato.find { it.id == id }?.nombre
                    Log.d(TAG, "Tipo de contrato seleccionado: $nombreTipoContrato (ID: $id)")
                }
            }
        }

        tipoContratoViewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                Log.e(TAG, "Error en tipos de contrato: $it")
                Toast.makeText(this, "Error al cargar tipos de contrato: $it", Toast.LENGTH_LONG).show()
                // Habilitar el spinner en caso de error
                autoComplete.isEnabled = true
            }
        }

        tipoContratoViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            // Deshabilitar el spinner durante la carga
            autoComplete.isEnabled = !isLoading
        }
    }

    private fun setupDistritoTrabajoSpinner() {
        distritoViewModel.distritosSpinner.observe(this) { distritos ->
            val adapter = CustomSpinnerAdapter(this, distritos)
            (spinnerDistritoTrabajo.editText as? AutoCompleteTextView)?.let { autoComplete ->
                autoComplete.setAdapter(adapter)
                
                // Si hay distritos disponibles, seleccionar el primero por defecto
                if (distritos.isNotEmpty()) {
                    autoComplete.setText(distritos[0].nombre, false)
                    selectedDistritoId = distritos[0].id
                }

                // Manejar la selección del distrito
                autoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val spinnerAdapter = parent.adapter as? CustomSpinnerAdapter<Int>
                    spinnerAdapter?.getSelectedItemId(position)?.let { id ->
                        selectedDistritoId = id
                        // Opcional: mostrar un mensaje de confirmación
                        val nombreDistrito = distritos.find { it.id == id }?.nombre
                        Toast.makeText(this, "Distrito seleccionado: $nombreDistrito (ID: $id)", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        // Observar errores
        distritoViewModel.error.observe(this) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(this, "Error al cargar distritos: $it", Toast.LENGTH_LONG).show()
            }
        }

        // Observar el estado de carga
        distritoViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Cargar los distritos
        distritoViewModel.cargarDistritos()
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
        
        // Validar campos requeridos
        camposRequeridos.forEach { (layout, editText) ->
            if (editText.text.toString().trim().isEmpty()) {
                layout.error = "Campo requerido"
                isValid = false
            } else {
                layout.error = null
            }
        }

        // Validar spinners
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

        // Validaciones específicas
        // Validar formato de teléfono (9 dígitos)
        val telefono = etTelefono.editText?.text.toString()
        if (telefono.isNotEmpty() && !telefono.matches(Regex("^[0-9]{9}$"))) {
            etTelefono.error = "Ingrese un número válido de 9 dígitos"
            isValid = false
        }

        // Validar que el ID sea numérico
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

            // Validar que los IDs estén seleccionados
            val idTipoContrato = selectedTipoContratoId
                ?: throw Exception("Debe seleccionar un tipo de contrato")
            val idDistrito = selectedDistritoId
                ?: throw Exception("Debe seleccionar un distrito")

            viewModel.registrarTrabajador(
                nombres = nombres,
                puesto = puesto,
                jefeInmediato = jefeInmediato,
                idTipoContrato = idTipoContrato,
                direccion = direccion,
                telefono = telefono,
                idDistritoTrabajo = idDistrito,
                password = id.toString() // Usando el ID como contraseña por defecto
            )
        } catch (e: Exception) {
            Toast.makeText(this, 
                "Error al procesar los datos: ${e.message}", 
                Toast.LENGTH_LONG).show()
        }
    }
} 