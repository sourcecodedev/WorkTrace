package com.upc.worktrace

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TrabajadoresActivity : BaseActivity() {
    
    private lateinit var rvTrabajadores: RecyclerView
    private lateinit var btnAgregarTrabajador: MaterialButton
    private lateinit var btnAtras: MaterialButton
    private lateinit var trabajadoresAdapter: TrabajadoresAdapter
    
    // Lista mutable de trabajadores para poder agregar nuevos
    private val trabajadores = arrayListOf<Trabajador>()
    
    // Lanzador para la actividad de agregar trabajador
    private val agregarTrabajadorLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val id = data.getStringExtra("TRABAJADOR_ID") ?: ""
                val nombre = data.getStringExtra("TRABAJADOR_NOMBRE") ?: ""
                val idExistente = data.getBooleanExtra("ID_EXISTENTE", false)
                
                if (!id.isEmpty() && !nombre.isEmpty() && !idExistente) {
                    val nuevoTrabajador = Trabajador(id, nombre)
                    
                    // Agregar el nuevo trabajador a la lista
                    trabajadores.add(nuevoTrabajador)
                    trabajadoresAdapter.notifyItemInserted(trabajadores.size - 1)
                    
                    // Guardar la lista actualizada
                    guardarTrabajadores()
                    
                    // Desplazar la vista al nuevo elemento
                    rvTrabajadores.smoothScrollToPosition(trabajadores.size - 1)
                    
                    // Mostrar mensaje de éxito
                    Snackbar.make(rvTrabajadores, "Trabajador agregado correctamente", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabajadores)
        
        // Configurar la barra superior
        setupToolbar(true, "Trabajadores")
        
        // Inicializar vistas
        rvTrabajadores = findViewById(R.id.rvTrabajadores)
        btnAgregarTrabajador = findViewById(R.id.btnAgregarTrabajador)
        btnAtras = findViewById(R.id.btnAtras)
        
        // Cargar la lista de trabajadores
        cargarTrabajadores()
        
        // Configurar RecyclerView
        trabajadoresAdapter = TrabajadoresAdapter(trabajadores) { trabajador ->
            // Acción al hacer clic en un trabajador
            val intent = Intent(this, DetallesTrabajadorActivity::class.java)
            intent.putExtra("TRABAJADOR_ID", trabajador.id)
            intent.putExtra("TRABAJADOR_NOMBRE", trabajador.nombre)
            startActivity(intent)
        }
        
        rvTrabajadores.layoutManager = LinearLayoutManager(this)
        rvTrabajadores.adapter = trabajadoresAdapter
        
        // Configurar el botón de agregar trabajador
        btnAgregarTrabajador.setOnClickListener {
            val intent = Intent(this, AgregarTrabajadorActivity::class.java)
            agregarTrabajadorLauncher.launch(intent)
        }
        
        // Configurar el botón atrás
        btnAtras.setOnClickListener {
            // Simplemente cerramos la actividad para volver a la anterior
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
    
    private fun agregarNuevoTrabajador(id: String, nombre: String): Boolean {
        // Verificar que no exista ya un trabajador con el mismo ID
        val existeTrabajador = trabajadores.any { it.id == id }
        
        if (!existeTrabajador) {
            // Crear y agregar el nuevo trabajador
            val nuevoTrabajador = Trabajador(id, nombre)
            trabajadores.add(nuevoTrabajador)
            
            // Notificar al adaptador que se ha añadido un elemento
            trabajadoresAdapter.notifyItemInserted(trabajadores.size - 1)
            
            // Desplazar la vista al nuevo elemento
            rvTrabajadores.smoothScrollToPosition(trabajadores.size - 1)
            
            // Mostrar mensaje de éxito
            Toast.makeText(this, "Trabajador agregado a la lista", Toast.LENGTH_SHORT).show()
            return true
        } else {
            // Mostrar mensaje de error
            Toast.makeText(this, "Ya existe un trabajador con el ID $id", Toast.LENGTH_LONG).show()
            return false
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
            
            fun bind(trabajador: Trabajador) {
                tvNombre.text = trabajador.nombre
                
                btnDetalle.setOnClickListener {
                    onItemClick(trabajador)
                }
                
                // También permitimos hacer clic en toda la fila
                itemView.setOnClickListener {
                    onItemClick(trabajador)
                }
            }
        }
    }
} 