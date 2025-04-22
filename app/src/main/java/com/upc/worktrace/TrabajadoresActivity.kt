package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class TrabajadoresActivity : AppCompatActivity() {
    
    private lateinit var rvTrabajadores: RecyclerView
    private lateinit var btnAgregarTrabajador: MaterialButton
    private lateinit var trabajadoresAdapter: TrabajadoresAdapter
    
    // Lista simulada de trabajadores
    private val trabajadores = arrayListOf(
        Trabajador("1234", "Yhimy Feria"),
        Trabajador("5678", "Ana Torres")
    )
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trabajadores)
        
        // Inicializar vistas
        rvTrabajadores = findViewById(R.id.rvTrabajadores)
        btnAgregarTrabajador = findViewById(R.id.btnAgregarTrabajador)
        
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
            startActivity(intent)
        }
    }
    
    // Clase de datos para representar un trabajador
    data class Trabajador(val id: String, val nombre: String)
    
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