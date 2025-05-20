package com.upc.worktrace

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class WorkerAssignedScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_assigned_schedule)

        val btnVolver = findViewById<MaterialButton>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            finish()
        }
    }
} 