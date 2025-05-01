package com.upc.worktrace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.upc.worktrace.ui.LoginActivity

class MarcacionUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marcacion_usuario)
        val toolbar = findViewById<Toolbar>(R.id.toolbar_worktrace)
        setSupportActionBar(toolbar)
        // Aquí puedes recibir el nombre del trabajador y mostrarlo si lo deseas

        val btnCerrarSesion = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        val llEntrada = findViewById<android.widget.LinearLayout>(R.id.llEntrada)
        val llSalida = findViewById<android.widget.LinearLayout>(R.id.llSalida)
        llEntrada.setOnClickListener {
            val intent = Intent(this, MarcarEntradaActivity::class.java)
            startActivity(intent)
        }
        llSalida.setOnClickListener {
            val intent = Intent(this, MarcarSalidaActivity::class.java)
            startActivity(intent)
        }

        val llHorario = findViewById<android.widget.LinearLayout>(R.id.llHorario)
        llHorario.setOnClickListener {
            val intent = Intent(this, VerHorarioActivity::class.java)
            startActivity(intent)
        }

        val llHistorial = findViewById<android.widget.LinearLayout>(R.id.llHistorial)
        llHistorial.setOnClickListener {
            val intent = Intent(this, HistorialAsistenciaUsuarioActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
} 