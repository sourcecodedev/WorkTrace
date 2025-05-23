package com.upc.worktrace.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.request.RastreoUbicacionRequest
import com.upc.worktrace.data.model.response.RastreoUbicacionResponse
import com.upc.worktrace.data.repository.RastreoUbicacionRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RastreoUbicacionViewModel : ViewModel() {
    private val TAG = "RastreoUbicacionVM"
    private val repository = RastreoUbicacionRepository("yhimy")

    private val _resultadoRastreo = MutableLiveData<RastreoUbicacionResponse>()
    val resultadoRastreo: LiveData<RastreoUbicacionResponse> = _resultadoRastreo

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    fun registrarRastreo(
        idAsistencia: Int = 1,
        latitud: Double,
        longitud: Double
    ) {
        viewModelScope.launch {
            try {
                _cargando.value = true
                Log.d(TAG, "Iniciando registro de rastreo...")

                val timestampFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val timestamp = timestampFormat.format(Date())

                val request = RastreoUbicacionRequest(
                    idAsistencia = idAsistencia,
                    timestampRegistro = timestamp,
                    latitud = latitud,
                    longitud = longitud
                )

                Log.d(TAG, """
                    |┌────── Datos de Rastreo ──────
                    |├── ID Asistencia: $idAsistencia
                    |├── Timestamp: $timestamp
                    |├── Latitud: $latitud
                    |└── Longitud: $longitud
                """.trimMargin())

                val response = repository.registrarRastreoUbicacion(request)
                _resultadoRastreo.value = response

                Log.d(TAG, """
                    |┌────── Resultado del Rastreo ──────
                    |├── Éxito: ${response.success}
                    |├── Mensaje: ${response.message}
                    |└── Código: ${response.statusCode}
                """.trimMargin())

            } catch (e: Exception) {
                Log.e(TAG, """
                    |┌────── Error en ViewModel ──────
                    |├── Tipo: ${e.javaClass.simpleName}
                    |├── Mensaje: ${e.message}
                    |└── Stack Trace:
                    |${e.stackTraceToString().prependIndent("    ")}
                """.trimMargin())

                _resultadoRastreo.value = RastreoUbicacionResponse(
                    success = false,
                    message = e.message ?: "Error al registrar rastreo",
                    statusCode = 500
                )
            } finally {
                _cargando.value = false
            }
        }
    }
} 