package com.upc.worktrace

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class WorkerCheckInActivity : AppCompatActivity() {
    private lateinit var tvFecha: TextView
    private lateinit var tvHora: TextView
    private val handler = Handler(Looper.getMainLooper())
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("es"))
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val timeZone = TimeZone.getTimeZone("America/Lima") 

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
        setContentView(R.layout.activity_worker_check_in)

        tvFecha = findViewById(R.id.tvFecha)
        tvHora = findViewById(R.id.tvHora)


        handler.post(updateTime)
    }

    override fun onDestroy() {
        super.onDestroy()

        handler.removeCallbacks(updateTime)
    }
} 