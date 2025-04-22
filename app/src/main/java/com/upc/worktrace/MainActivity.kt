package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    
    private lateinit var cvGestionarTrabajadores: MaterialCardView
    private lateinit var cvAsignarHorarios: MaterialCardView
    private lateinit var cvVerAsistencia: MaterialCardView
    private lateinit var cvExportarReporte: MaterialCardView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Inicializar vistas
        cvGestionarTrabajadores = findViewById(R.id.cvGestionarTrabajadores)
        cvAsignarHorarios = findViewById(R.id.cvAsignarHorarios)
        cvVerAsistencia = findViewById(R.id.cvVerAsistencia)
        cvExportarReporte = findViewById(R.id.cvExportarReporte)
        
        // Configurar listeners
        cvGestionarTrabajadores.setOnClickListener {
            val intent = Intent(this, TrabajadoresActivity::class.java)
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
            val intent = Intent(this, ExportarReporteActivity::class.java)
            startActivity(intent)
        }
    }
} 