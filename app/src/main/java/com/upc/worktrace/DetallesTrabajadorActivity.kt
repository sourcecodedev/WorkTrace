package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class DetallesTrabajadorActivity : BaseActivity() {
    
    private lateinit var tvId: TextView
    private lateinit var tvNombre: TextView
    private lateinit var btnEditar: MaterialButton
    private lateinit var btnAtras: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
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
                try {
                    Log.d("DetallesTrabajador", "Botón editar presionado")
                    val intent = Intent(this, EditarTrabajadorActivity::class.java)
                    intent.putExtra("TRABAJADOR_ID", id)
                    intent.putExtra("TRABAJADOR_NOMBRE", nombre)
                    Log.d("DetallesTrabajador", "Intent creado con datos: ID=$id, NOMBRE=$nombre")
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("DetallesTrabajador", "Error al abrir EditarTrabajadorActivity", e)
                    Toast.makeText(this, "Error al abrir la ventana de edición: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
            
            // Configurar el botón atrás
            btnAtras.setOnClickListener {
                finish()
            }
            
        } catch (e: Exception) {
            Log.e("DetallesTrabajador", "Error en onCreate", e)
            Toast.makeText(this, "Error al iniciar la actividad: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }
} 