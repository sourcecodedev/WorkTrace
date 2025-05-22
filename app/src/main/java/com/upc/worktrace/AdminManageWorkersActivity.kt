package com.upc.worktrace

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.activity.result.ActivityResult
import com.upc.worktrace.data.model.Trabajador

class AdminManageWorkersActivity : BaseActivity() {
    
    private lateinit var rvTrabajadores: RecyclerView
    private lateinit var btnAgregarTrabajador: MaterialButton
    private lateinit var btnAtras: MaterialButton
    private lateinit var etNombre: EditText
    private lateinit var spinnerCantidad: Spinner
    private lateinit var trabajadoresAdapter: TrabajadoresAdapter
    

    private val trabajadores = ArrayList<Trabajador>()
    

    private val agregarTrabajadorLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val id = data.getStringExtra("TRABAJADOR_ID") ?: ""
                val nombre = data.getStringExtra("TRABAJADOR_NOMBRE") ?: ""
                val idExistente = data.getBooleanExtra("ID_EXISTENTE", false)
                
                if (id.isNotEmpty() && nombre.isNotEmpty() && !idExistente) {
                    val nuevoTrabajador = Trabajador(id, nombre, "usuario")
                    

                    trabajadores.add(nuevoTrabajador)
                    trabajadoresAdapter.notifyItemInserted(trabajadores.size - 1)
                    

                    guardarTrabajadores()
                    

                    rvTrabajadores.smoothScrollToPosition(trabajadores.size - 1)
                    

                    Snackbar.make(rvTrabajadores, "Trabajador agregado correctamente", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_manage_workers)
        
        // Configurar la barra superior
        setupToolbar(true, "Trabajadores")
        
        // Inicializar vistas
        rvTrabajadores = findViewById(R.id.rvTrabajadores)
        btnAgregarTrabajador = findViewById(R.id.btnAgregarTrabajador)
        btnAtras = findViewById(R.id.btnAtras)
        etNombre = findViewById(R.id.etNombre)
        spinnerCantidad = findViewById(R.id.spinnerCantidad)


        val items = listOf("1 - 10", "11 - 20", "21 - 30")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCantidad.adapter = adapter
        

        cargarTrabajadores()
        

        trabajadoresAdapter = TrabajadoresAdapter(trabajadores) { trabajador ->
            // Acción al hacer clic en un trabajador
            val intent = Intent(this, AdminWorkerDetailActivity::class.java)
            intent.putExtra("TRABAJADOR_ID", trabajador.id)
            intent.putExtra("TRABAJADOR_NOMBRE", trabajador.nombre)
            startActivity(intent)
        }
        
        rvTrabajadores.layoutManager = LinearLayoutManager(this)
        rvTrabajadores.adapter = trabajadoresAdapter
        
        // Configurar el botón de agregar trabajador
        btnAgregarTrabajador.setOnClickListener {
            val intent = Intent(this, AdminAddWorkerActivity::class.java)
            agregarTrabajadorLauncher.launch(intent)
        }
        
        // Configurar el botón atrás
        btnAtras.setOnClickListener {
            finish()
        }
    }
    
    private fun cargarTrabajadores() {
        val sharedPreferences = getSharedPreferences("trabajadores_prefs", Context.MODE_PRIVATE)
        val trabajadoresJson = sharedPreferences.getString("trabajadores", null)
        
        if (trabajadoresJson != null) {
            val type = object : TypeToken<List<Trabajador>>() {}.type
            val trabajadoresCargados = Gson().fromJson<List<Trabajador>>(trabajadoresJson, type)
            
            // Limpiar la lista actual y agregar los trabajadores cargados
            trabajadores.clear()
            trabajadores.addAll(trabajadoresCargados)
        } else {
            // Si no hay datos guardados, agregar algunos trabajadores de ejemplo
            trabajadores.add(Trabajador("1234", "Yhimy Feria"))
            trabajadores.add(Trabajador("5678", "Ana Torres"))
            
            // Guardar estos datos iniciales
            guardarTrabajadores()
        }
    }
    
    private fun guardarTrabajadores() {
        val sharedPreferences = getSharedPreferences("trabajadores_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val trabajadoresJson = Gson().toJson(trabajadores)
        editor.putString("trabajadores", trabajadoresJson)
        editor.apply()
    }
    
    // Adapter para la lista de trabajadores
    inner class TrabajadoresAdapter(
        private val trabajadores: List<Trabajador>,
        private val onItemClick: (Trabajador) -> Unit
    ) : RecyclerView.Adapter<TrabajadoresAdapter.TrabajadorViewHolder>() {
        
        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): TrabajadorViewHolder {
            val view = android.view.LayoutInflater.from(parent.context)
                .inflate(R.layout.item_trabajador, parent, false)
            return TrabajadorViewHolder(view)
        }
        
        override fun onBindViewHolder(holder: TrabajadorViewHolder, position: Int) {
            val trabajador = trabajadores[position]
            holder.bind(trabajador)
        }
        
        override fun getItemCount() = trabajadores.size
        
        inner class TrabajadorViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
            private val tvNombre: android.widget.TextView = itemView.findViewById(R.id.tvNombre)
            private val btnDetalle: android.widget.ImageButton = itemView.findViewById(R.id.btnDetalle)
            private val btnEliminar: android.widget.ImageButton = itemView.findViewById(R.id.btnEliminar)
            
            fun bind(trabajador: Trabajador) {
                tvNombre.text = trabajador.nombre
                
                btnDetalle.setOnClickListener {
                    onItemClick(trabajador)
                }
                
                // También permitimos hacer clic en toda la fila
                itemView.setOnClickListener {
                    onItemClick(trabajador)
                }

                btnEliminar.setOnClickListener {
                    AlertDialog.Builder(itemView.context)
                        .setTitle("Eliminar trabajador")
                        .setMessage("¿Estás seguro de que deseas eliminar a ${trabajador.nombre}?")
                        .setPositiveButton("Sí") { dialog, _ ->
                            val position = adapterPosition
                            if (position != RecyclerView.NO_POSITION) {
                                (trabajadores as ArrayList).removeAt(position)
                                notifyItemRemoved(position)
                                guardarTrabajadores()
                                Snackbar.make(
                                    itemView,
                                    "Trabajador eliminado correctamente",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                            dialog.dismiss()
                        }
                        .setNegativeButton("No") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }
        }
    }
} 