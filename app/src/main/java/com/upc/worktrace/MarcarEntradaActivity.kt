package com.upc.worktrace

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MarcarEntradaActivity : AppCompatActivity() {
    private lateinit var tvFecha: TextView
    private lateinit var tvHora: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("es"))
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val timeZone = TimeZone.getTimeZone("America/Lima") // UTC-5

    init {
        timeFormat.timeZone = timeZone
    }

    private val updateTime = object : Runnable {
        override fun run() {
            val currentTime = Calendar.getInstance(timeZone)
            tvFecha.text = dateFormat.format(currentTime.time)
            tvHora.text = timeFormat.format(currentTime.time)
            handler.postDelayed(this, 1000) // Actualizar cada segundo
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marcar_entrada)

        tvFecha = findViewById(R.id.tvFecha)
        tvHora = findViewById(R.id.tvHora)

        // Iniciar la actualización de la hora
        handler.post(updateTime)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Detener la actualización de la hora cuando la actividad se destruye
        handler.removeCallbacks(updateTime)
    }
} 