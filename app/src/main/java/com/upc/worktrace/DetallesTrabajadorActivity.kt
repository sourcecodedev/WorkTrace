package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class DetallesTrabajadorActivity : BaseActivity() {
    
    private lateinit var tvId: TextView
    private lateinit var tvNombre: TextView
    private lateinit var btnEditar: MaterialButton
    private lateinit var btnAtras: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_trabajador)
        
        // Obtener datos del intent
        val id = intent.getStringExtra("TRABAJADOR_ID") ?: ""
        val nombre = intent.getStringExtra("TRABAJADOR_NOMBRE") ?: ""
        
        // Configurar la barra superior
        setupToolbar(true, nombre)
        
        // Inicializar vistas
        tvId = findViewById(R.id.tvId)
        tvNombre = findViewById(R.id.tvNombre)
        btnEditar = findViewById(R.id.btnEditar)
        btnAtras = findViewById(R.id.btnAtras)
        
        // Mostrar datos del trabajador
        tvId.text = id
        tvNombre.text = nombre
        
        // Configurar el botón de editar
        btnEditar.setOnClickListener {
            // Abrir la actividad de edición con los datos del trabajador
            val intent = Intent(this, EditarTrabajadorActivity::class.java)
            intent.putExtra("TRABAJADOR_ID", id)
            intent.putExtra("TRABAJADOR_NOMBRE", nombre)
            startActivity(intent)
        }
        
        // Configurar el botón atrás
        btnAtras.setOnClickListener {
            // Simplemente cerramos la actividad para volver a la anterior
            finish()
        }
    }
} 