package com.upc.worktrace

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class EditarTrabajadorActivity : BaseActivity() {
    
    private lateinit var etId: TextInputLayout
    private lateinit var etNombre: TextInputLayout
    private lateinit var btnGuardar: MaterialButton
    private lateinit var btnCancelar: MaterialButton
    private lateinit var btnEliminar: MaterialButton
    
    // Variables para guardar los datos originales
    private var idOriginal = ""
    private var nombreOriginal = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_trabajador)
        
        // Obtener datos del intent
        idOriginal = intent.getStringExtra("TRABAJADOR_ID") ?: ""
        nombreOriginal = intent.getStringExtra("TRABAJADOR_NOMBRE") ?: ""
        
        // Configurar la barra superior
        setupToolbar(true, "Editar Trabajador")
        
        // Inicializar vistas
        etId = findViewById(R.id.etId)
        etNombre = findViewById(R.id.etNombre)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)
        btnEliminar = findViewById(R.id.btnEliminar)
        
        // Mostrar datos actuales del trabajador
        etId.editText?.setText(idOriginal)
        etNombre.editText?.setText(nombreOriginal)
        
        // Configurar el botón de guardar
        btnGuardar.setOnClickListener {
            if (validarDatos()) {
                mostrarDialogoConfirmacionActualizar()
            }
        }
        
        // Configurar el botón de cancelar
        btnCancelar.setOnClickListener {
            // Simplemente cerramos la actividad para volver a la anterior
            finish()
        }
        
        // Configurar el botón de eliminar
        btnEliminar.setOnClickListener {
            mostrarDialogoConfirmacionEliminar()
        }
    }
    
    private fun mostrarDialogoConfirmacionActualizar() {
        val id = etId.editText?.text.toString().trim()
        val nombre = etNombre.editText?.text.toString().trim()
        
        AlertDialog.Builder(this)
            .setTitle("Confirmar actualización")
            .setMessage("¿Está seguro que desea actualizar los datos del trabajador?")
            .setPositiveButton("Aceptar") { _, _ ->
                // Aquí iría la lógica para actualizar el trabajador en una base de datos
                // Por ahora, simplemente mostramos un mensaje y regresamos a la pantalla anterior
                Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Cancelar") { _, _ ->
                // Restaurar valores originales
                etId.editText?.setText(idOriginal)
                etNombre.editText?.setText(nombreOriginal)
            }
            .show()
    }
    
    private fun mostrarDialogoConfirmacionEliminar() {
        AlertDialog.Builder(this)
            .setTitle("Confirmar eliminación")
            .setMessage("¿Está seguro que desea eliminar al trabajador ${nombreOriginal}?")
            .setPositiveButton("Aceptar") { _, _ ->
                // Aquí iría la lógica para eliminar al trabajador de la base de datos
                // Por ahora, simplemente mostramos un mensaje y regresamos a la pantalla anterior
                Toast.makeText(this, "Trabajador eliminado correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Cancelar", null) // No hacemos nada al cancelar, simplemente se cierra el diálogo
            .show()
    }
    
    private fun validarDatos(): Boolean {
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
        
        return isValid
    }
} 