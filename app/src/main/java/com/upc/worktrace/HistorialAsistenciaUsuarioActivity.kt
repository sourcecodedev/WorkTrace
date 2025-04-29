package com.upc.worktrace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class HistorialAsistenciaUsuarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_asistencia_usuario)

        val btnVolver = findViewById<MaterialButton>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()
        }
    }
} 