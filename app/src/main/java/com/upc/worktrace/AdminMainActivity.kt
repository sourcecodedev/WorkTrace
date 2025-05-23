package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import androidx.appcompat.widget.Toolbar

class AdminMainActivity : BaseActivity() {
    
    private lateinit var cvGestionarTrabajadores: MaterialCardView
    private lateinit var cvAsignarHorarios: MaterialCardView
    private lateinit var cvVerAsistencia: MaterialCardView
    private lateinit var cvExportarReporte: MaterialCardView
    
    private val TAG = "MainActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_admin_main)
            val toolbar = findViewById<Toolbar>(R.id.toolbar_worktrace)
            setSupportActionBar(toolbar)
            setupToolbar(false)
            

            initializeViews()
            

            setupClickListeners()

            val btnCerrarSesion = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnCerrarSesion)
            btnCerrarSesion.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al inicializar MainActivity: ${e.message}")
            e.printStackTrace()
            Toast.makeText(this, "Error al cargar la pantalla principal", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun initializeViews() {
        try {
            cvGestionarTrabajadores = findViewById(R.id.cvGestionarTrabajadores)
            cvAsignarHorarios = findViewById(R.id.cvAsignarHorarios)
            cvVerAsistencia = findViewById(R.id.cvVerAsistencia)
            cvExportarReporte = findViewById(R.id.cvExportarReporte)
            

            if (!::cvGestionarTrabajadores.isInitialized ||
                !::cvAsignarHorarios.isInitialized ||
                !::cvVerAsistencia.isInitialized ||
                !::cvExportarReporte.isInitialized) {
                throw RuntimeException("No se pudieron inicializar todas las vistas")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al inicializar vistas: ${e.message}")
            throw e
        }
    }
    
    private fun setupClickListeners() {

        cvGestionarTrabajadores.setOnClickListener {
            val intent = Intent(this, AdminManageWorkersActivity::class.java)
            startActivity(intent)
        }
        
        cvAsignarHorarios.setOnClickListener {
            val intent = Intent(this, AsignarHorariosActivity::class.java)
            startActivity(intent)
        }
        
        cvVerAsistencia.setOnClickListener {
            val intent = Intent(this, HistorialAsistenciaActivity::class.java)
            startActivity(intent)
        }
        
        cvExportarReporte.setOnClickListener {
            val intent = Intent(this, AdminReportActivity::class.java)
            startActivity(intent)
        }
    }
} 