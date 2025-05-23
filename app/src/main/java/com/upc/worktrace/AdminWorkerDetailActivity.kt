package com.upc.worktrace

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class AdminWorkerDetailActivity : BaseActivity() {
    
    private lateinit var tvId: TextView
    private lateinit var tvNombre: TextView
    private lateinit var tvRol: TextView
    private lateinit var tvPuesto: TextView
    private lateinit var tvJefeInmediato: TextView
    private lateinit var tvTipoContrato: TextView
    private lateinit var tvDireccion: TextView
    private lateinit var tvTelefono: TextView
    private lateinit var tvDistritoAsignado: TextView
    private lateinit var btnEditar: MaterialButton
    private lateinit var btnAtras: MaterialButton
    
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_admin_worker_detail)
            

            val id = intent.getIntExtra("TRABAJADOR_ID", 0)
            val nombre = intent.getStringExtra("TRABAJADOR_NOMBRE") ?: "-"
            val rol = intent.getStringExtra("TRABAJADOR_ROL") ?: "-"
            val puesto = intent.getStringExtra("TRABAJADOR_PUESTO") ?: "-"
            val jefeInmediato = intent.getStringExtra("TRABAJADOR_JEFE") ?: "-"
            val tipoContrato = intent.getStringExtra("TRABAJADOR_CONTRATO") ?: "-"
            val direccion = intent.getStringExtra("TRABAJADOR_DIRECCION") ?: "-"
            val telefono = intent.getStringExtra("TRABAJADOR_TELEFONO") ?: "-"
            val distrito = intent.getStringExtra("TRABAJADOR_DISTRITO") ?: "-"
            

            setupToolbar(true, nombre)
            

            tvId = findViewById(R.id.tvId)
            tvNombre = findViewById(R.id.tvNombre)
            tvRol = findViewById(R.id.tvRol)
            tvPuesto = findViewById(R.id.tvPuesto)
            tvJefeInmediato = findViewById(R.id.tvJefeInmediato)
            tvTipoContrato = findViewById(R.id.tvTipodecontrato)
            tvDireccion = findViewById(R.id.tvDirección)
            tvTelefono = findViewById(R.id.tvTelefono)
            tvDistritoAsignado = findViewById(R.id.tvDistritoAsignado)
            btnEditar = findViewById(R.id.btnEditar)
            btnAtras = findViewById(R.id.btnAtras)
            

            tvId.text = "ID: $id"
            tvNombre.text = nombre
            tvRol.text = rol
            tvPuesto.text = puesto
            tvJefeInmediato.text = jefeInmediato
            tvTipoContrato.text = tipoContrato
            tvDireccion.text = direccion
            tvTelefono.text = telefono
            tvDistritoAsignado.text = distrito
            

            btnEditar.setOnClickListener {
                try {
                    Log.d("DetallesTrabajador", "Botón editar presionado")
                    val intent = Intent(this, EditarTrabajadorActivity::class.java)
                    intent.putExtra("idTrabajador", id)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("DetallesTrabajador", "Error al abrir EditarTrabajadorActivity", e)
                    Toast.makeText(this, "Error al abrir la ventana de edición: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
            

            btnAtras.setOnClickListener {
                finish()
            }
            
        } catch (e: Exception) {
            Log.e("DetallesTrabajador", "Error en onCreate", e)
            Toast.makeText(this, "Error al iniciar la actividad: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }
} 