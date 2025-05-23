package com.upc.worktrace

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.*
import com.google.android.material.button.MaterialButton
import com.upc.worktrace.viewmodel.MarcarAsistenciaViewModel
import java.text.SimpleDateFormat
import java.util.*

class WorkerCheckInActivity : BaseActivity() {
    private val TAG = "WorkerCheckInActivity"
    private lateinit var tvFecha: TextView
    private lateinit var tvHora: TextView
    private lateinit var tvUbicacion: TextView
    private lateinit var btnMarcar: MaterialButton
    private lateinit var btnAtras: MaterialButton
    private val handler = Handler(Looper.getMainLooper())
    private val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("es"))
    private val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val timeZone = TimeZone.getTimeZone("America/Lima") 
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val LOCATION_PERMISSION_REQUEST_CODE = 1000
    private lateinit var viewModel: MarcarAsistenciaViewModel
    private var currentLatitude: Double = 0.0
    private var currentLongitude: Double = 0.0
    private var currentLocationText: String = ""

    private val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 10000 // 10 segundos
        fastestInterval = 5000 // 5 segundos
    }

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
        Log.d(TAG, "Iniciando WorkerCheckInActivity")
        setContentView(R.layout.activity_worker_check_in)

        setupToolbar(true, "Marcar Entrada")
        initializeViews()
        setupViewModel()
        setupCurrentDateTime()
        setupLocationServices()
        setupListeners()

        handler.post(updateTime)
    }

    private fun setupViewModel() {
        Log.d(TAG, "Configurando ViewModel")
        viewModel = ViewModelProvider(this)[MarcarAsistenciaViewModel::class.java]
        
        viewModel.resultadoMarcacion.observe(this) { resultado ->
            Log.d(TAG, """
                |┌────── Resultado de Marcación ──────
                |├── Éxito: ${resultado.success}
                |├── Mensaje: ${resultado.message}
                |└── Código: ${resultado.statusCode}
            """.trimMargin())

            if (resultado.success) {
                Toast.makeText(this, "Marcación registrada exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, resultado.message ?: "Error al registrar marcación", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.cargando.observe(this) { cargando ->
            Log.d(TAG, "Estado de carga actualizado: $cargando")
            btnMarcar.isEnabled = !cargando
            if (cargando) {
                // Aquí podrías mostrar un progress bar si lo deseas
            }
        }
    }

    private fun initializeViews() {
        Log.d(TAG, "Inicializando vistas")
        tvFecha = findViewById(R.id.tvFecha)
        tvHora = findViewById(R.id.tvHora)
        tvUbicacion = findViewById(R.id.lvltextUbicacion)
        btnMarcar = findViewById(R.id.btnMarcarEntrada)
        btnAtras = findViewById(R.id.btnAtras)

        btnMarcar.isEnabled = false
        tvUbicacion.text = "Obteniendo ubicación..."
    }

    private fun setupCurrentDateTime() {
        Log.d(TAG, "Configurando fecha y hora inicial")
        val calendar = Calendar.getInstance()
        tvFecha.text = dateFormat.format(calendar.time)
        tvHora.text = timeFormat.format(calendar.time)
    }

    private fun setupLocationServices() {
        Log.d(TAG, "Configurando servicios de ubicación")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    Log.d(TAG, """
                        |┌────── Nueva Ubicación Recibida ──────
                        |├── Latitud: ${location.latitude}
                        |├── Longitud: ${location.longitude}
                        |├── Precisión: ${location.accuracy} metros
                        |└── Proveedor: ${location.provider}
                    """.trimMargin())
                    
                    currentLatitude = location.latitude
                    currentLongitude = location.longitude
                    updateLocationUI(location)
                }
            }
        }

        if (checkLocationPermission()) {
            startLocationUpdates()
        } else {
            requestLocationPermission()
        }
    }

    private fun startLocationUpdates() {
        try {
            if (checkLocationPermission()) {
                Log.d(TAG, "Iniciando actualizaciones de ubicación")
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Error al acceder a la ubicación", e)
            tvUbicacion.text = "Error al acceder a la ubicación"
            btnMarcar.isEnabled = false
        }
    }

    private fun stopLocationUpdates() {
        Log.d(TAG, "Deteniendo actualizaciones de ubicación")
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun updateLocationUI(location: android.location.Location) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            Log.d(TAG, "Obteniendo dirección para ubicación: ${location.latitude}, ${location.longitude}")
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                val distrito = address.subLocality ?: address.locality ?: ""
                val codigoPostal = address.postalCode ?: ""
                
                Log.d(TAG, """
                    |┌────── Dirección Obtenida ──────
                    |├── Distrito: $distrito
                    |├── Código Postal: $codigoPostal
                    |├── Dirección Completa: ${address.getAddressLine(0)}
                    |└── País: ${address.countryName}
                """.trimMargin())

                currentLocationText = if (distrito.isNotEmpty() || codigoPostal.isNotEmpty()) {
                    "$codigoPostal $distrito"
                } else {
                    "Ubicación encontrada"
                }
                tvUbicacion.text = currentLocationText
                btnMarcar.isEnabled = true
            } else {
                Log.w(TAG, "No se encontró dirección para la ubicación")
                tvUbicacion.text = "Dirección no encontrada"
                btnMarcar.isEnabled = false
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener dirección", e)
            tvUbicacion.text = "Error al obtener dirección"
            btnMarcar.isEnabled = false
        }
    }

    private fun setupListeners() {
        btnMarcar.setOnClickListener {
            val idTrabajador = intent.getIntExtra("id_trabajador", 0)
            val idHorarioAsignacion = intent.getIntExtra("id_horario_asignacion", 0)

            Log.d(TAG, """
                |┌────── Datos para Marcación ──────
                |├── ID Trabajador: $idTrabajador
                |├── ID Horario: $idHorarioAsignacion
                |├── Ubicación Actual:
                |│   ├── Latitud: $currentLatitude
                |│   ├── Longitud: $currentLongitude
                |│   └── Texto: $currentLocationText
                |└── Fecha/Hora: ${dateFormat.format(Date())} ${timeFormat.format(Date())}
            """.trimMargin())

            if (idTrabajador == 0 || idHorarioAsignacion == 0) {
                Log.e(TAG, "Error: Datos de trabajador no disponibles")
                Toast.makeText(this, "Error: Datos de trabajador no disponibles", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            viewModel.registrarMarcacion(
                idTrabajador = idTrabajador,
                idHorarioAsignacion = idHorarioAsignacion,
                latitud = currentLatitude,
                longitud = currentLongitude,
                ubicacionTexto = currentLocationText
            )
        }
        
        btnAtras.setOnClickListener {
            Log.d(TAG, "Botón atrás presionado, finalizando actividad")
            finish()
        }
    }

    private fun checkLocationPermission(): Boolean {
        val hasPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        
        Log.d(TAG, "Verificando permiso de ubicación: ${if (hasPermission) "Concedido" else "Denegado"}")
        return hasPermission
    }

    private fun requestLocationPermission() {
        Log.d(TAG, "Solicitando permiso de ubicación")
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                val permissionGranted = grantResults.isNotEmpty() && 
                                      grantResults[0] == PackageManager.PERMISSION_GRANTED
                
                Log.d(TAG, """
                    |┌────── Resultado Permiso Ubicación ──────
                    |├── Concedido: $permissionGranted
                    |└── Código: $requestCode
                """.trimMargin())

                if (permissionGranted) {
                    startLocationUpdates()
                } else {
                    tvUbicacion.text = "Se requiere permiso de ubicación"
                    btnMarcar.isEnabled = false
                    Toast.makeText(
                        this,
                        "Se requiere permiso de ubicación para continuar",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "Activity resumida")
        if (checkLocationPermission()) {
            startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "Activity pausada")
        stopLocationUpdates()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Activity destruida")
        handler.removeCallbacks(updateTime)
    }
} 