package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class EditarTrabajadorActivity : BaseActivity() {
    
    private lateinit var etId: TextInputLayout
    private lateinit var etNombre: TextInputLayout
    private lateinit var btnGuardar: MaterialButton
    private lateinit var btnCancelar: MaterialButton
    

    private var idOriginal = ""
    private var nombreOriginal = ""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            Log.d("EditarTrabajador", "onCreate iniciado")
            
            setContentView(R.layout.activity_editar_trabajador)
            Log.d("EditarTrabajador", "Layout cargado")
            

            idOriginal = intent.getStringExtra("TRABAJADOR_ID") ?: ""
            nombreOriginal = intent.getStringExtra("TRABAJADOR_NOMBRE") ?: ""
            Log.d("EditarTrabajador", "Datos recibidos: ID=$idOriginal, NOMBRE=$nombreOriginal")
            

            setupToolbar(true, "Editar Trabajador")
            Log.d("EditarTrabajador", "Toolbar configurado")
            

            etId = findViewById(R.id.etId)
            etNombre = findViewById(R.id.etNombre)
            btnGuardar = findViewById(R.id.btnGuardar)
            btnCancelar = findViewById(R.id.btnCancelar)
            Log.d("EditarTrabajador", "Vistas inicializadas")
            

            etId.editText?.setText(idOriginal)
            etNombre.editText?.setText(nombreOriginal)
            Log.d("EditarTrabajador", "Datos mostrados en campos")
            

            btnGuardar.setOnClickListener {
                if (validarDatos()) {
                    mostrarDialogoConfirmacionActualizar()
                }
            }
            

            btnCancelar.setOnClickListener {
                finish()
            }
            
            Log.d("EditarTrabajador", "Activity configurada correctamente")
            
        } catch (e: Exception) {
            Log.e("EditarTrabajador", "Error al iniciar la activity", e)
            Toast.makeText(this, "Error al abrir la ventana de edición: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }
    
    private fun mostrarDialogoConfirmacionActualizar() {
        val id = etId.editText?.text.toString().trim()
        val nombre = etNombre.editText?.text.toString().trim()
        
        AlertDialog.Builder(this)
            .setTitle("Confirmar actualización")
            .setMessage("¿Está seguro que desea actualizar los datos del trabajador?")
            .setPositiveButton("Aceptar") { _, _ ->
                Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Cancelar") { _, _ ->

                etId.editText?.setText(idOriginal)
                etNombre.editText?.setText(nombreOriginal)
            }
            .show()
    }
    
    private fun validarDatos(): Boolean {
        val id = etId.editText?.text.toString().trim()
        val nombre = etNombre.editText?.text.toString().trim()
        

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