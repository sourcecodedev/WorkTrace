package com.upc.worktrace

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class ExportarReporteActivity : AppCompatActivity() {
    
    private lateinit var btnExportar: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exportar_reporte)
        
        // Inicializar vistas
        btnExportar = findViewById(R.id.btnExportar)
        
        // Configurar el botón
        btnExportar.setOnClickListener {
            // Aquí iría la lógica para exportar los reportes
            // Por ahora, simplemente mostramos un mensaje
            Toast.makeText(this, "Reporte exportado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
} 