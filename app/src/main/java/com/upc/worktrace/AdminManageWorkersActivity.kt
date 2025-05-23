package com.upc.worktrace

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.upc.worktrace.data.model.entities.Trabajador
import com.upc.worktrace.viewmodel.WorkerViewModel
import java.text.SimpleDateFormat  // Para los datos de ejemplo
import java.util.*  // Para los datos de ejemplo

class AdminManageWorkersActivity : BaseActivity() {
    private val TAG = "AdminManageWorkersActivity"
    
    private lateinit var rvTrabajadores: RecyclerView
    private lateinit var btnAgregarTrabajador: MaterialButton
    private lateinit var btnAtras: MaterialButton
    private lateinit var etNombre: EditText
    private lateinit var spinnerCantidad: Spinner
    private lateinit var trabajadoresAdapter: TrabajadoresAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var tvEmptyState: TextView
    
    private val trabajadores = ArrayList<Trabajador>()
    private val viewModel: WorkerViewModel by viewModels()
    
    private val agregarTrabajadorLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            cargarTrabajadores()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_admin_manage_workers)
            
            setupToolbar(true, "Trabajadores")
            
            initializeViews()
            setupSpinner()
            setupRecyclerView()
            setupListeners()
            setupObservers()
            cargarTrabajadores()
        } catch (e: Exception) {
            Log.e(TAG, "Error en onCreate", e)
            mostrarError("Error al inicializar la pantalla")
        }
    }

    private fun initializeViews() {
        try {
            rvTrabajadores = findViewById(R.id.rvTrabajadores)
            btnAgregarTrabajador = findViewById(R.id.btnAgregarTrabajador)
            btnAtras = findViewById(R.id.btnAtras)
            etNombre = findViewById(R.id.etNombre)
            spinnerCantidad = findViewById(R.id.spinnerCantidad)
            progressBar = findViewById(R.id.progressBar)
            tvEmptyState = findViewById(R.id.tvEmptyState)
        } catch (e: Exception) {
            Log.e(TAG, "Error al inicializar vistas", e)
            throw e
        }
    }

    private fun setupObservers() {
        viewModel.trabajadores.observe(this) { listaTrabajadores ->
            trabajadores.clear()
            if (listaTrabajadores.isNotEmpty()) {
                trabajadores.addAll(listaTrabajadores)
                tvEmptyState.visibility = View.GONE
                rvTrabajadores.visibility = View.VISIBLE
            } else {
                tvEmptyState.visibility = View.VISIBLE
                rvTrabajadores.visibility = View.GONE
            }
            trabajadoresAdapter.notifyDataSetChanged()
        }

        viewModel.cargando.observe(this) { estaCargando ->
            progressBar.visibility = if (estaCargando) View.VISIBLE else View.GONE
        }

        viewModel.resultadoTrabajador.observe(this) { resultado ->
            if (resultado.success) {
                Toast.makeText(this, "Trabajador eliminado correctamente", Toast.LENGTH_SHORT).show()
            } else {
                mostrarError(resultado.message ?: "Error en la operación")
            }
        }
    }

    private fun cargarTrabajadores() {
        viewModel.obtenerTrabajadores()
    }

    // Datos de ejemplo comentados para referencia
    /*
    private fun cargarDatosEjemplo() {
        val fechaActual = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        
        val datosEjemplo = listOf(
            Trabajador(
                idTrabajador = 1,
                idUsuario = 101,
                nombres = "Juan Pérez García",
                puesto = "Desarrollador Senior",
                jefeInmediato = "Carlos García",
                idTipoContrato = "1",
                direccion = "Av. Principal 123, San Isidro",
                telefono = "987654321",
                idDistritoTrabajo = 1,
                estado = 1,
                fechaRegistro = fechaActual,
                idUsuarioCreador = 1
            ),
            Trabajador(
                idTrabajador = 2,
                idUsuario = 102,
                nombres = "María López Torres",
                puesto = "Diseñadora UX",
                jefeInmediato = "Ana Torres",
                idTipoContrato = "2",
                direccion = "Calle Las Flores 456, Miraflores",
                telefono = "987654322",
                idDistritoTrabajo = 2,
                estado = 1,
                fechaRegistro = fechaActual,
                idUsuarioCreador = 1
            ),
            Trabajador(
                idTrabajador = 3,
                idUsuario = 103,
                nombres = "Pedro Ramírez Silva",
                puesto = "Project Manager",
                jefeInmediato = "Luis Vargas",
                idTipoContrato = "3",
                direccion = "Jr. Los Pinos 789, San Borja",
                telefono = "987654323",
                idDistritoTrabajo = 3,
                estado = 1,
                fechaRegistro = fechaActual,
                idUsuarioCreador = 1
            ),
            Trabajador(
                idTrabajador = 4,
                idUsuario = 104,
                nombres = "Ana Torres Mendoza",
                puesto = "QA Engineer",
                jefeInmediato = "Juan Pérez",
                idTipoContrato = "1",
                direccion = "Av. Los Álamos 234, La Molina",
                telefono = "987654324",
                idDistritoTrabajo = 4,
                estado = 1,
                fechaRegistro = fechaActual,
                idUsuarioCreador = 1
            ),
            Trabajador(
                idTrabajador = 5,
                idUsuario = 105,
                nombres = "Carlos García Ruiz",
                puesto = "DevOps Engineer",
                jefeInmediato = "María López",
                idTipoContrato = "2",
                direccion = "Calle Las Palmeras 567, Surco",
                telefono = "987654325",
                idDistritoTrabajo = 5,
                estado = 1,
                fechaRegistro = fechaActual,
                idUsuarioCreador = 1
            )
        )
        
        trabajadores.clear()
        trabajadores.addAll(datosEjemplo)
        trabajadoresAdapter.notifyDataSetChanged()
    }
    */

    private fun setupSpinner() {
        try {
            val items = listOf("1 - 10", "11 - 20", "21 - 30")
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerCantidad.adapter = adapter
        } catch (e: Exception) {
            Log.e(TAG, "Error al configurar spinner", e)
            mostrarError("Error al configurar filtros")
        }
    }

    private fun setupRecyclerView() {
        try {
            trabajadoresAdapter = TrabajadoresAdapter(
                trabajadores,
                onItemClick = { trabajador -> abrirDetallesTrabajador(trabajador) },
                onEliminarClick = { trabajador -> mostrarDialogoConfirmacionEliminar(trabajador) }
            )
            
            rvTrabajadores.apply {
                adapter = trabajadoresAdapter
                layoutManager = LinearLayoutManager(this@AdminManageWorkersActivity)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al configurar RecyclerView", e)
            mostrarError("Error al configurar la lista de trabajadores")
        }
    }

    private fun mostrarDialogoConfirmacionEliminar(trabajador: Trabajador) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Está seguro que desea eliminar al trabajador ${trabajador.nombres}?")
            .setPositiveButton("Eliminar") { _, _ ->
                try {
                    viewModel.eliminarTrabajador(trabajador.idTrabajador)
                } catch (e: Exception) {
                    Log.e(TAG, "Error al eliminar trabajador", e)
                    mostrarError("Error al eliminar trabajador: ${e.message}")
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun abrirDetallesTrabajador(trabajador: Trabajador) {
        try {
            val intent = Intent(this, AdminWorkerDetailActivity::class.java).apply {
                putExtra("TRABAJADOR_ID", trabajador.idTrabajador)
                putExtra("TRABAJADOR_USUARIO_ID", trabajador.idUsuario)
                putExtra("TRABAJADOR_NOMBRE", trabajador.nombres)
                putExtra("TRABAJADOR_PUESTO", trabajador.puesto)
                putExtra("TRABAJADOR_JEFE", trabajador.jefeInmediato)
                putExtra("TRABAJADOR_CONTRATO", trabajador.idTipoContrato)
                putExtra("TRABAJADOR_DIRECCION", trabajador.direccion)
                putExtra("TRABAJADOR_TELEFONO", trabajador.telefono)
                putExtra("TRABAJADOR_DISTRITO", trabajador.idDistritoTrabajo)
                putExtra("TRABAJADOR_ESTADO", trabajador.estado)
                putExtra("TRABAJADOR_FECHA_REGISTRO", trabajador.fechaRegistro)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        } catch (e: Exception) {
            Log.e(TAG, "Error al abrir detalles del trabajador", e)
            mostrarError("Error al abrir detalles del trabajador")
        }
    }

    private fun setupListeners() {
        try {
            btnAgregarTrabajador.setOnClickListener {
                val intent = Intent(this, AdminAddWorkerActivity::class.java)
                agregarTrabajadorLauncher.launch(intent)
            }

            btnAtras.setOnClickListener {
                finish()
            }

            etNombre.addTextChangedListener(object : android.text.TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: android.text.Editable?) {
                    viewModel.filtrarTrabajadores(s?.toString() ?: "")
                }
            })
        } catch (e: Exception) {
            Log.e(TAG, "Error al configurar listeners", e)
            mostrarError("Error al configurar la interacción")
        }
    }

    private fun mostrarError(mensaje: String) {
        try {
            Snackbar.make(
                findViewById(android.R.id.content),
                mensaje,
                Snackbar.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Log.e(TAG, "Error al mostrar Snackbar", e)
            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        }
    }

    inner class TrabajadoresAdapter(
        private var trabajadores: ArrayList<Trabajador>,
        private val onItemClick: (Trabajador) -> Unit,
        private val onEliminarClick: (Trabajador) -> Unit
    ) : RecyclerView.Adapter<TrabajadoresAdapter.TrabajadorViewHolder>() {
        
        fun actualizarLista(nuevaLista: ArrayList<Trabajador>) {
            trabajadores = nuevaLista
            notifyDataSetChanged()
        }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrabajadorViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trabajador, parent, false)
            return TrabajadorViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
            try {
                val trabajador = trabajadores[position]
                holder.bind(trabajador)
            } catch (e: Exception) {
                Log.e(TAG, "Error en onBindViewHolder", e)
            }
        }
        
        override fun getItemCount() = trabajadores.size
        
        inner class TrabajadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val tvNombre: TextView = itemView.findViewById(R.id.tvNombreTrabajador)
            private val tvPuesto: TextView = itemView.findViewById(R.id.tvPuesto)
            private val tvDistrito: TextView = itemView.findViewById(R.id.tvDistrito)
            private val btnDetalle: ImageButton = itemView.findViewById(R.id.btnVerDetalles)
            private val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminar)
            
            fun bind(trabajador: Trabajador) {
                try {
                    tvNombre.text = trabajador.nombres
                    tvPuesto.text = trabajador.puesto
                    tvDistrito.text = "Distrito: ${trabajador.idDistritoTrabajo}"
                    
                    itemView.setOnClickListener { 
                        onItemClick(trabajador)
                    }
                    
                    btnDetalle.setOnClickListener { 
                        onItemClick(trabajador)
                    }
                    
                    btnEliminar.setOnClickListener { 
                        onEliminarClick(trabajador)
                    }
                    
                } catch (e: Exception) {
                    Log.e(TAG, "Error al bindear trabajador", e)
                }
            }
        }
    }

    private fun abrirEditarTrabajador(trabajador: Trabajador) {
        try {
            val intent = Intent(this, EditarTrabajadorActivity::class.java).apply {
                putExtra("idTrabajador", trabajador.idTrabajador)
            }
            startActivity(intent)
        } catch (e: Exception) {
            Log.e(TAG, "Error al abrir pantalla de edición", e)
            mostrarError("Error al abrir la pantalla de edición")
        }
    }
}