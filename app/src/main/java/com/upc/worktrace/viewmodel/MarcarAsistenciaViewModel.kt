package com.upc.worktrace.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.request.MarcarAsistenciaRequest
import com.upc.worktrace.data.model.response.MarcarAsistenciaResponse
import com.upc.worktrace.data.repository.MarcarAsistenciaRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MarcarAsistenciaViewModel : ViewModel() {
    private val TAG = "MarcarAsistenciaVM"
    private val repository = MarcarAsistenciaRepository("yhimy")

    private val _resultadoMarcacion = MutableLiveData<MarcarAsistenciaResponse>()
    val resultadoMarcacion: LiveData<MarcarAsistenciaResponse> = _resultadoMarcacion

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    fun registrarMarcacion(
        idTrabajador: Int,
        idHorarioAsignacion: Int,
        latitud: Double,
        longitud: Double,
        ubicacionTexto: String,
        fuente: String = "APP"
    ) {
        Log.d(TAG, """
            |┌────── Iniciando Registro de Marcación ──────
            |├── ID Trabajador: $idTrabajador
            |├── ID Horario Asignación: $idHorarioAsignacion
            |├── Ubicación: 
            |│   ├── Latitud: $latitud
            |│   ├── Longitud: $longitud
            |│   └── Texto: $ubicacionTexto
            |└── Fuente: $fuente
        """.trimMargin())

        viewModelScope.launch {
            try {
                _cargando.value = true
                Log.d(TAG, "Estado de carga actualizado: Cargando")
                
                // Obtener fecha y hora actual en formato requerido
                val calendar = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                
                val fecha = dateFormat.format(calendar.time)
                val horaMarcacion = timeFormat.format(calendar.time)

                Log.d(TAG, """
                    |┌────── Datos Temporales ──────
                    |├── Fecha: $fecha
                    |└── Hora: $horaMarcacion
                """.trimMargin())

                val request = MarcarAsistenciaRequest(
                    id_trabajador = idTrabajador,
                    id_horario_asignacion = idHorarioAsignacion,
                    fecha = fecha,
                    hora_marcacion = horaMarcacion,
                    latitud = latitud,
                    longitud = longitud,
                    ubicacion_texto = ubicacionTexto,
                    fuente = fuente
                )

                Log.d(TAG, "Enviando petición al repositorio...")
                val response = repository.registrarMarcacionAsistencia(request)
                
                Log.d(TAG, """
                    |┌────── Respuesta Recibida ──────
                    |├── Éxito: ${response.success}
                    |├── Mensaje: ${response.message}
                    |├── Código: ${response.statusCode}
                    |└── Cuerpo: ${response.bodyString}
                """.trimMargin())

                _resultadoMarcacion.value = response
                
            } catch (e: Exception) {
                Log.e(TAG, """
                    |┌────── Error en ViewModel ──────
                    |├── Tipo: ${e.javaClass.simpleName}
                    |├── Mensaje: ${e.message}
                    |└── Stack Trace: 
                    |${e.stackTraceToString().prependIndent("    ")}
                """.trimMargin())
                
                _resultadoMarcacion.value = MarcarAsistenciaResponse(
                    success = false,
                    message = e.message ?: "Error al registrar marcación",
                    statusCode = 500
                )
            } finally {
                _cargando.value = false
                Log.d(TAG, "Estado de carga actualizado: Completado")
            }
        }
    }
} 