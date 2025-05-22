package com.upc.worktrace

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.upc.worktrace.data.model.Trabajador

class AdminAddWorkerActivity : BaseActivity() {
    
    private lateinit var etId: TextInputLayout
    private lateinit var etNombre: TextInputLayout
    private lateinit var btnGuardar: MaterialButton
    private lateinit var btnCancelar: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_add_worker)
        
        // Configurar la barra superior
        setupToolbar(true, "Agregar Trabajador")
        
        // Inicializar vistas
        etId = findViewById(R.id.etId)
        etNombre = findViewById(R.id.etNombre)
        btnGuardar = findViewById(R.id.btnGuardar)
        btnCancelar = findViewById(R.id.btnCancelar)
        
        // Configurar el botón de guardar
        btnGuardar.setOnClickListener {
            // Verificar campos antes de mostrar el diálogo
            validarYGuardarTrabajador()
        }
        
        // Configurar el botón de cancelar
        btnCancelar.setOnClickListener {
            // Simplemente cerramos la actividad para volver a la anterior
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
    
    private fun validarYGuardarTrabajador() {
        val id = etId.editText?.text.toString().trim()
        val nombre = etNombre.editText?.text.toString().trim()

        // Verificar que los campos no estén vacíos
        if (id.isEmpty() || nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificar si el ID ya existe
        val idExistente = verificarIdExistente(id)
        
        if (idExistente) {
            Toast.makeText(this, "El ID ya está registrado", Toast.LENGTH_LONG).show()
            return
        }
        
        // Si todo está bien, mostrar diálogo de confirmación
        mostrarDialogoConfirmacion(id, nombre)
    }
    
    private fun mostrarDialogoConfirmacion(id: String, nombre: String) {
        // Crear el intent con los datos del trabajador
        val resultIntent = Intent()
        resultIntent.putExtra("TRABAJADOR_ID", id)
        resultIntent.putExtra("TRABAJADOR_NOMBRE", nombre)
        resultIntent.putExtra("TRABAJADOR_USUARIO", nombre)
        resultIntent.putExtra("TRABAJADOR_PASSWORD", id)
        resultIntent.putExtra("TRABAJADOR_ROL", "usuario")
        resultIntent.putExtra("ID_EXISTENTE", false)
        
        // Mostrar diálogo de confirmación
        AlertDialog.Builder(this)
            .setTitle("Confirmar registro")
            .setMessage("¿Está seguro de agregar al trabajador $nombre?")
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
                // Mostrar mensaje de éxito
                Toast.makeText(this, "Trabajador registrado correctamente", Toast.LENGTH_SHORT).show()
                
                // Enviar resultado y cerrar la actividad después de un breve momento
                Handler(Looper.getMainLooper()).postDelayed({
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }, 1000)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
    
    private fun guardarTrabajador() {
        validarYGuardarTrabajador()
    }
    
    private fun verificarIdExistente(id: String): Boolean {
        // Obtener la lista de trabajadores desde SharedPreferences
        val sharedPreferences = getSharedPreferences("trabajadores_prefs", Context.MODE_PRIVATE)
        val trabajadoresJson = sharedPreferences.getString("trabajadores", null)
        
        if (trabajadoresJson != null) {
            val type = object : TypeToken<List<Trabajador>>() {}.type
            val trabajadores = Gson().fromJson<List<Trabajador>>(trabajadoresJson, type)
            
            // Verificar si existe algún trabajador con el mismo ID
            return trabajadores.any { it.id == id }
        }
        
        return false
    }
} 