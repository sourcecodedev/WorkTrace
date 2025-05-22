package com.upc.worktrace

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.upc.worktrace.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var usernameInput: TextInputLayout
    private lateinit var passwordInput: TextInputLayout
    private lateinit var loginButton: MaterialButton

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // Configurar la barra de estado
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        WindowCompat.setDecorFitsSystemWindows(window, true)

        setContentView(R.layout.activity_login)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        usernameInput = findViewById(R.id.etUsuario)
        passwordInput = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnIngresar)

        loginButton.setOnClickListener {
            val username = usernameInput.editText?.text.toString().trim()
            val password = passwordInput.editText?.text.toString().trim()

            if (username.isEmpty()) {
                usernameInput.error = "El usuario no puede estar vacío"
                return@setOnClickListener
            } else {
                usernameInput.error = null
            }

            if (password.isEmpty()) {
                passwordInput.error = "La contraseña no puede estar vacía"
                return@setOnClickListener
            } else {
                passwordInput.error = null
            }

            viewModel.login(username, password)
        }


        viewModel.loginResult.observe(this) { response ->
            if (response.success) {
                Toast.makeText(this, "Login exitoso!", Toast.LENGTH_SHORT).show()

                if (response.user?.role == "ADMIN") {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()

                } else {
                    val intent = Intent(this, WorkerMainProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("TRABAJADOR_ID", response.user?.id)
                    intent.putExtra("TRABAJADOR_NOMBRE", response.user?.username)
                    startActivity(intent)
                    finish()
                }


            } else {
                Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
            }

        }



        // Inicializar vistas
        /*etUsuario = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etPassword)
        btnIngresar = findViewById(R.id.btnIngresar)
        */
        // Configurar listeners
        /*
        btnIngresar.setOnClickListener {
            viewModel.login(username = etUsuario.editText?.text.toString(), password = etPassword.editText?.text.toString())
            //login()
        }*/
    }
    /*
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
            try {
                // Login exitoso
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                finish() // Cerramos esta actividad para que el usuario no pueda volver atrás
            } catch (e: Exception) {
                // Capturar cualquier excepción que pueda ocurrir durante la navegación
                Toast.makeText(this, "Error al cargar la pantalla principal: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        } else {
            // Validar contra trabajadores registrados
            val sharedPreferences = getSharedPreferences("trabajadores_prefs", MODE_PRIVATE)
            val trabajadoresJson = sharedPreferences.getString("trabajadores", null)
            if (trabajadoresJson != null) {
                val type = object : com.google.gson.reflect.TypeToken<List<Trabajador>>() {}.type
                val trabajadores = com.google.gson.Gson().fromJson<List<Trabajador>>(trabajadoresJson, type)
                val trabajador = trabajadores.find { it.nombre == usuario && it.id == password && it.rol == "usuario" }
                if (trabajador != null) {
                    // Login exitoso de trabajador
                    val intent = Intent(this, WorkerMainProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.putExtra("TRABAJADOR_ID", trabajador.id)
                    intent.putExtra("TRABAJADOR_NOMBRE", trabajador.nombre)
                    startActivity(intent)
                    finish()
                    return
                }
            }
            // Login fallido
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }
 */
}