package com.upc.worktrace.data.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.upc.worktrace.data.model.request.MarcarAsistenciaRequest
import com.upc.worktrace.data.model.response.MarcarAsistenciaResponse
import com.upc.worktrace.data.remote.RetrofitClient
import retrofit2.HttpException
import java.net.UnknownHostException

class MarcarAsistenciaRepository(owner: String, baseUrl: String? = null) {
    private val TAG = "MarcarAsistenciaRepo"
    private val api = RetrofitClient.getClient(owner, baseUrl)
    private val gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun registrarMarcacionAsistencia(request: MarcarAsistenciaRequest): MarcarAsistenciaResponse {
        Log.d(TAG, """
            |┌────── Iniciando Petición al API ──────
            |├── URL Base: ${RetrofitClient.getBaseUrl()}
            |├── Endpoint: /v1/marcacion
            |└── Request Body: 
            |${gson.toJson(request).prependIndent("    ")}
        """.trimMargin())

        return try {
            val response = api.registrarMarcacionAsistencia(request)
            
            Log.d(TAG, """
                |┌────── Respuesta del API ──────
                |├── Success: ${response.success}
                |├── Status Code: ${response.statusCode}
                |├── Mensaje: ${response.message}
                |├── Headers: ${response.headers}
                |└── Body: ${response.bodyString?.let { 
                    try {
                        gson.toJson(gson.fromJson(it, Any::class.java))
                    } catch (e: Exception) {
                        it
                    }
                }}
            """.trimMargin())
            
            if (!response.success) {
                Log.w(TAG, """
                    |┌────── Advertencia ──────
                    |├── La marcación no fue exitosa
                    |├── Código: ${response.statusCode}
                    |└── Mensaje: ${response.message}
                """.trimMargin())
            }
            
            response
        } catch (e: HttpException) {
            Log.e(TAG, """
                |┌────── Error HTTP ──────
                |├── Código: ${e.code()}
                |├── Mensaje: ${e.message()}
                |├── URL: ${e.response()?.raw()?.request?.url}
                |└── Response Body: ${e.response()?.errorBody()?.string()}
            """.trimMargin())
            
            MarcarAsistenciaResponse(
                success = false,
                message = when (e.code()) {
                    400 -> "Datos de marcación inválidos"
                    401 -> "No autorizado"
                    403 -> "Acceso denegado"
                    404 -> "Servicio no encontrado"
                    500 -> "Error en el servidor"
                    else -> "Error de red: ${e.code()}"
                },
                statusCode = e.code(),
                bodyString = e.response()?.errorBody()?.string()
            )
        } catch (e: UnknownHostException) {
            Log.e(TAG, """
                |┌────── Error de Conexión ──────
                |├── Tipo: ${e.javaClass.simpleName}
                |├── Mensaje: ${e.message}
                |└── Stack Trace:
                |${e.stackTraceToString().prependIndent("    ")}
            """.trimMargin())
            
            MarcarAsistenciaResponse(
                success = false,
                message = "No hay conexión a internet",
                statusCode = -1
            )
        } catch (e: Exception) {
            Log.e(TAG, """
                |┌────── Error Inesperado ──────
                |├── Tipo: ${e.javaClass.simpleName}
                |├── Mensaje: ${e.message}
                |└── Stack Trace:
                |${e.stackTraceToString().prependIndent("    ")}
            """.trimMargin())
            
            MarcarAsistenciaResponse(
                success = false,
                message = e.message ?: "Error al registrar marcación",
                statusCode = 500,
                bodyString = e.stackTraceToString()
            )
        }
    }
} 