package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    
    private lateinit var etUsuario: TextInputLayout
    private lateinit var etPassword: TextInputLayout
    private lateinit var btnIngresar: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        
        // Inicializar vistas
        etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnIngresar = findViewById(R.id.btnIngresar)
        
        // Configurar listeners
        btnIngresar.setOnClickListener {
            login()
        }
    }
    
    private fun login() {
        val usuario = etUsuario.editText?.text.toString().trim()
        val password = etPassword.editText?.text.toString().trim()
        
        // Validación básica
        if (usuario.isEmpty()) {
            etUsuario.error = "El usuario no puede estar vacío"
            return
        } else {
            etUsuario.error = null
        }
        
        if (password.isEmpty()) {
            etPassword.error = "La contraseña no puede estar vacía"
            return
        } else {
            etPassword.error = null
        }
        
        // Aquí iría la validación real contra una API o base de datos
        // Por ahora, simplemente verificamos si es "admin"/"admin" para simular
        if (usuario == "admin" && password == "admin") {
            // Login exitoso
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cerramos esta actividad para que el usuario no pueda volver atrás
        } else {
            // Login fallido
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
} 