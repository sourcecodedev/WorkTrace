package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class DetallesTrabajadorActivity : AppCompatActivity() {
    
    private lateinit var tvId: TextView
    private lateinit var tvNombre: TextView
    private lateinit var btnEditar: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_trabajador)
        
        // Inicializar vistas
        tvId = findViewById(R.id.tvId)
        tvNombre = findViewById(R.id.tvNombre)
        btnEditar = findViewById(R.id.btnEditar)
        
        // Obtener datos del intent
        val id = intent.getStringExtra("TRABAJADOR_ID") ?: ""
        val nombre = intent.getStringExtra("TRABAJADOR_NOMBRE") ?: ""
        
        // Mostrar datos del trabajador
        tvId.text = id
        tvNombre.text = nombre
        
        // Título de la pantalla con el nombre del trabajador
        title = nombre
        
        // Configurar el botón de editar
        btnEditar.setOnClickListener {
            // Abrir la actividad de edición con los datos del trabajador
            val intent = Intent(this, EditarTrabajadorActivity::class.java)
            intent.putExtra("TRABAJADOR_ID", id)
            intent.putExtra("TRABAJADOR_NOMBRE", nombre)
            startActivity(intent)
        }
    }
} 