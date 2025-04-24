package com.upc.worktrace

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class ExportarReporteActivity : BaseActivity() {
    
    private lateinit var btnExportar: MaterialButton
    private lateinit var btnCancelar: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exportar_reporte)
        
        // Configurar la barra superior
        setupToolbar(true, "Exportar Reporte")
        
        // Inicializar vistas
        btnExportar = findViewById(R.id.btnExportar)
        btnCancelar = findViewById(R.id.btnCancelar)
        
        // Configurar el botón exportar
        btnExportar.setOnClickListener {
            // Aquí iría la lógica para exportar los reportes
            // Por ahora, simplemente mostramos un mensaje
            Toast.makeText(this, "Reporte exportado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
        
        // Configurar el botón cancelar
        btnCancelar.setOnClickListener {
            // Simplemente cerramos la actividad para volver a la anterior
            finish()
        }
    }
} 