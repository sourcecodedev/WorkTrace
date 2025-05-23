package com.upc.worktrace.data.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.upc.worktrace.data.model.request.RastreoUbicacionRequest
import com.upc.worktrace.data.model.response.RastreoUbicacionResponse
import com.upc.worktrace.data.remote.RetrofitClient
import retrofit2.HttpException
import java.net.UnknownHostException

class RastreoUbicacionRepository(owner: String, baseUrl: String? = null) {
    private val TAG = "RastreoUbicacionRepo"
    private val api = RetrofitClient.getClient(owner, baseUrl)
    private val gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun registrarRastreoUbicacion(request: RastreoUbicacionRequest): RastreoUbicacionResponse {
        Log.d(TAG, """
            |┌────── Iniciando Registro de Rastreo ──────
            |├── ID Asistencia: ${request.idAsistencia}
            |├── Timestamp: ${request.timestampRegistro}
            |├── Latitud: ${request.latitud}
            |└── Longitud: ${request.longitud}
        """.trimMargin())

        return try {
            val response = api.registrarRastreoUbicacion(request)
            
            Log.d(TAG, """
                |┌────── Respuesta del API ──────
                |├── Success: ${response.success}
                |├── Status Code: ${response.statusCode}
                |├── Mensaje: ${response.message}
                |└── Body: ${response.bodyString}
            """.trimMargin())
            
            if (!response.success) {
                Log.w(TAG, "El registro de rastreo no fue exitoso: ${response.message}")
            }
            
            response
        } catch (e: HttpException) {
            Log.e(TAG, """
                |┌────── Error HTTP ──────
                |├── Código: ${e.code()}
                |├── Mensaje: ${e.message()}
                |└── Response Body: ${e.response()?.errorBody()?.string()}
            """.trimMargin())
            
            RastreoUbicacionResponse(
                success = false,
                message = when (e.code()) {
                    400 -> "Datos de rastreo inválidos"
                    401 -> "No autorizado"
                    403 -> "Acceso denegado"
                    404 -> "Servicio no encontrado"
                    500 -> "Error en el servidor"
                    else -> "Error de red: ${e.code()}"
                },
                statusCode = e.code()
            )
        } catch (e: UnknownHostException) {
            Log.e(TAG, "Error de conexión", e)
            RastreoUbicacionResponse(
                success = false,
                message = "No hay conexión a internet",
                statusCode = -1
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado", e)
            RastreoUbicacionResponse(
                success = false,
                message = e.message ?: "Error al registrar rastreo",
                statusCode = 500
            )
        }
    }
} 