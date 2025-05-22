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
            
            // Obtener datos del intent
            val id = intent.getStringExtra("TRABAJADOR_ID") ?: ""
            val nombre = intent.getStringExtra("TRABAJADOR_NOMBRE") ?: ""
            val rol = intent.getStringExtra("TRABAJADOR_ROL") ?: ""
            val puesto = intent.getStringExtra("TRABAJADOR_PUESTO") ?: ""
            val jefeInmediato = intent.getStringExtra("TRABAJADOR_JEFE") ?: ""
            val tipoContrato = intent.getStringExtra("TRABAJADOR_CONTRATO") ?: ""
            val direccion = intent.getStringExtra("TRABAJADOR_DIRECCION") ?: ""
            val telefono = intent.getStringExtra("TRABAJADOR_TELEFONO") ?: ""
            val distrito = intent.getStringExtra("TRABAJADOR_DISTRITO") ?: ""
            
            // Configurar la barra superior
            setupToolbar(true, nombre)
            
            // Inicializar vistas
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
            
            // Mostrar datos del trabajador
            tvId.text = id
            tvNombre.text = nombre
            tvRol.text = rol
            tvPuesto.text = puesto
            tvJefeInmediato.text = jefeInmediato
            tvTipoContrato.text = tipoContrato
            tvDireccion.text = direccion
            tvTelefono.text = telefono
            tvDistritoAsignado.text = distrito
            
            // Configurar el botón de editar
            btnEditar.setOnClickListener {
                try {
                    Log.d("DetallesTrabajador", "Botón editar presionado")
                    val intent = Intent(this, EditarTrabajadorActivity::class.java)
                    intent.putExtra("TRABAJADOR_ID", id)
                    intent.putExtra("TRABAJADOR_NOMBRE", nombre)
                    intent.putExtra("TRABAJADOR_ROL", rol)
                    intent.putExtra("TRABAJADOR_PUESTO", puesto)
                    intent.putExtra("TRABAJADOR_JEFE", jefeInmediato)
                    intent.putExtra("TRABAJADOR_CONTRATO", tipoContrato)
                    intent.putExtra("TRABAJADOR_DIRECCION", direccion)
                    intent.putExtra("TRABAJADOR_TELEFONO", telefono)
                    intent.putExtra("TRABAJADOR_DISTRITO", distrito)
                    Log.d("DetallesTrabajador", "Intent creado con datos del trabajador")
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("DetallesTrabajador", "Error al abrir EditarTrabajadorActivity", e)
                    Toast.makeText(this, "Error al abrir la ventana de edición: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
            
            // Configurar el botón atrás
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