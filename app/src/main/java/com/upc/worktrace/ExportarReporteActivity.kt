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
        

        setupToolbar(true, "Exportar Reporte")


        btnExportar = findViewById(R.id.btnExportar)
        btnCancelar = findViewById(R.id.btnCancelar)
        

        btnExportar.setOnClickListener {

            Toast.makeText(this, "Reporte exportado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }
        

        btnCancelar.setOnClickListener {

            finish()
        }
    }
} 