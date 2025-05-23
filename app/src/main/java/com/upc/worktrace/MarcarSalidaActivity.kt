package com.upc.worktrace

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.upc.worktrace.viewmodel.MarcarSalidaModel
import java.text.SimpleDateFormat
import java.util.*

class MarcarSalidaActivity : AppCompatActivity() {

    private lateinit var marcarSalidaViewModel: MarcarSalidaModel
    private lateinit var tvFecha: TextView
    private lateinit var tvHora: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("es"))
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val timeZone = TimeZone.getTimeZone("America/Lima")
    private lateinit var btnMarcarSalida: MaterialButton
    init {
        timeFormat.timeZone = timeZone
    }

    private val updateTime = object : Runnable {
        override fun run() {
            val currentTime = Calendar.getInstance(timeZone)
            tvFecha.text = dateFormat.format(currentTime.time)
            tvHora.text = timeFormat.format(currentTime.time)
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marcar_salida)
        marcarSalidaViewModel = ViewModelProvider(this)[MarcarSalidaModel::class.java]
        tvFecha = findViewById(R.id.tvFecha)
        tvHora = findViewById(R.id.tvHora)
        btnMarcarSalida = findViewById(R.id.btnMarcarSalida)

        btnMarcarSalida.setOnClickListener {
            marcarSalidaViewModel.registrarSalida(
                idAsistencia = "A001", // ID real o dinámico
                tipoMarcacion = "SALIDA",
                fechaMarcacion = tvFecha.text.toString(),
                horaMarcacion = tvHora.text.toString(),
                latitud = "-12.0464", // Coordenadas simuladas o GPS real
                longitud = "-77.0428",
                ubicacion = "Lima, Perú",
                fuente = "APP"
            )
        }

        marcarSalidaViewModel.resultadoMarcarSalida.observe(this) { respuesta ->
            if (respuesta.success) {
                Toast.makeText(this, "Salida registrada correctamente", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Error: ${respuesta.message}", Toast.LENGTH_LONG).show()
            }
        }

        marcarSalidaViewModel.cargando.observe(this) { cargando ->
            btnMarcarSalida.isEnabled = !cargando
        }

        handler.post(updateTime)
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacks(updateTime)
    }
} 