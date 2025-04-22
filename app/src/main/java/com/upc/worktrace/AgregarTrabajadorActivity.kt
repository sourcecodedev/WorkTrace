package com.upc.worktrace

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class AgregarTrabajadorActivity : AppCompatActivity() {
    
    private lateinit var etId: TextInputLayout
    private lateinit var etNombre: TextInputLayout
    private lateinit var btnGuardar: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_trabajador)
        
        // Inicializar vistas
        etId = findViewById(R.id.etId)
        etNombre = findViewById(R.id.etNombre)
        btnGuardar = findViewById(R.id.btnGuardar)
        
        // Configurar el botón de guardar
        btnGuardar.setOnClickListener {
            guardarTrabajador()
        }
    }
    
    private fun guardarTrabajador() {
        val id = etId.editText?.text.toString().trim()
        val nombre = etNombre.editText?.text.toString().trim()
        
        // Validación
        var isValid = true
        
        if (id.isEmpty()) {
            etId.error = "El ID no puede estar vacío"
            isValid = false
        } else {
            etId.error = null
        }
        
        if (nombre.isEmpty()) {
            etNombre.error = "El nombre no puede estar vacío"
            isValid = false
        } else {
            etNombre.error = null
        }
        
        if (isValid) {
            // Aquí iría la lógica para guardar el trabajador en una base de datos
            // Por ahora, simplemente mostramos un mensaje y cerramos la actividad
            Toast.makeText(this, "Trabajador guardado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
} 