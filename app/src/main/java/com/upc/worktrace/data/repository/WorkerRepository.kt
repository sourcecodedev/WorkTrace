package com.upc.worktrace.data.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.WorkerBodyResponse
import com.upc.worktrace.data.model.response.WorkerResponse
import com.upc.worktrace.data.remote.RetrofitClient
import retrofit2.HttpException
import java.net.UnknownHostException

class WorkerRepository(
    ownerName: String,
    baseUrl: String? = "https://20mpw85kof.execute-api.us-east-1.amazonaws.com/v1/"
) {
    private val TAG = "WorkerRepository"
    private val apiService = RetrofitClient.getClient(ownerName, baseUrl)
    private val gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun obtenerTodosTrabajadores(): WorkerResponse {
        return try {
            Log.d(TAG, "Iniciando petición para obtener todos los trabajadores")
            
            val response = apiService.obtenerTodosTrabajadores()
            Log.d(TAG, "Respuesta recibida: ${gson.toJson(response)}")
            Log.d(TAG, "Headers: ${response.headers}")
            Log.d(TAG, "Status Code: ${response.statusCode}")
            
            // Parsear el body string a objeto
            val bodyResponse = response.bodyString?.let { bodyStr ->
                try {
                    gson.fromJson(bodyStr, WorkerBodyResponse::class.java)
                } catch (e: Exception) {
                    Log.e(TAG, "Error al parsear body response", e)
                    null
                }
            }
            
            if (bodyResponse?.success == true) {
                Log.d(TAG, "Trabajadores obtenidos: ${bodyResponse.data?.size ?: 0}")
                response.copy(
                    success = true,
                    data = bodyResponse.data
                )
            } else {
                Log.w(TAG, "La respuesta no fue exitosa")
                Log.w(TAG, "Cuerpo de la respuesta: ${response.bodyString}")
                response
            }
            
        } catch (e: HttpException) {
            Log.e(TAG, "Error HTTP: ${e.code()}", e)
            Log.e(TAG, "Response Body: ${e.response()?.errorBody()?.string()}")
            WorkerResponse(
                success = false,
                message = when (e.code()) {
                    500 -> "Error en el servidor"
                    else -> "Error de red: ${e.code()}"
                },
                statusCode = e.code(),
                data = null,
                bodyString = e.response()?.errorBody()?.string()
            )
        } catch (e: UnknownHostException) {
            Log.e(TAG, "Error de conexión", e)
            WorkerResponse(
                success = false,
                message = "No hay conexión a internet",
                statusCode = -1,
                data = null,
                bodyString = null
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado", e)
            WorkerResponse(
                success = false,
                message = e.message ?: "Error al obtener trabajadores",
                statusCode = 500,
                data = null,
                bodyString = e.stackTraceToString()
            )
        }
    }

    suspend fun obtenerTrabajadorPorId(idTrabajador: Int): WorkerResponse {
        return try {
            Log.d(TAG, "Iniciando petición para obtener trabajador con ID: $idTrabajador")
            
            val response = apiService.obtenerTrabajadorPorId(idTrabajador)
            Log.d(TAG, "Respuesta recibida: ${gson.toJson(response)}")
            Log.d(TAG, "Headers: ${response.headers}")
            Log.d(TAG, "Status Code: ${response.statusCode}")
            
            // Parsear el body string a objeto
            val bodyResponse = response.bodyString?.let { bodyStr ->
                try {
                    gson.fromJson(bodyStr, WorkerBodyResponse::class.java)
                } catch (e: Exception) {
                    Log.e(TAG, "Error al parsear body response", e)
                    null
                }
            }
            
            if (bodyResponse?.success == true && bodyResponse.data?.isNotEmpty() == true) {
                Log.d(TAG, "Trabajador obtenido: ${bodyResponse.data.first()}")
                response.copy(
                    success = true,
                    data = bodyResponse.data
                )
            } else {
                Log.w(TAG, "La respuesta no fue exitosa o no se encontró el trabajador")
                Log.w(TAG, "Cuerpo de la respuesta: ${response.bodyString}")
                response
            }
            
        } catch (e: HttpException) {
            Log.e(TAG, "Error HTTP: ${e.code()}", e)
            Log.e(TAG, "Response Body: ${e.response()?.errorBody()?.string()}")
            WorkerResponse(
                success = false,
                message = when (e.code()) {
                    404 -> "Trabajador no encontrado"
                    500 -> "Error en el servidor"
                    else -> "Error de red: ${e.code()}"
                },
                statusCode = e.code(),
                data = null,
                bodyString = e.response()?.errorBody()?.string()
            )
        } catch (e: UnknownHostException) {
            Log.e(TAG, "Error de conexión", e)
            WorkerResponse(
                success = false,
                message = "No hay conexión a internet",
                statusCode = -1,
                data = null,
                bodyString = null
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error inesperado", e)
            WorkerResponse(
                success = false,
                message = e.message ?: "Error al obtener trabajador",
                statusCode = 500,
                data = null,
                bodyString = e.stackTraceToString()
            )
        }
    }

    suspend fun eliminarTrabajador(idTrabajador: Int): WorkerResponse {
        return try {
            Log.d(TAG, "Iniciando petición eliminarTrabajador - ID: $idTrabajador")
            
            val response = apiService.eliminarTrabajador(idTrabajador)
            Log.d(TAG, "Respuesta de eliminación: ${gson.toJson(response)}")
            Log.d(TAG, "Headers: ${response.headers}")
            Log.d(TAG, "Status Code: ${response.statusCode}")
            
            if (!response.success) {
                Log.w(TAG, "La eliminación no fue exitosa: ${response.message}")
                Log.w(TAG, "Cuerpo de la respuesta: ${response.bodyString}")
            }
            
            response
        } catch (e: HttpException) {
            Log.e(TAG, "Error HTTP al eliminar: ${e.code()}", e)
            Log.e(TAG, "Response Body: ${e.response()?.errorBody()?.string()}")
            WorkerResponse(
                success = false,
                message = when (e.code()) {
                    404 -> "Trabajador no encontrado"
                    500 -> "Error en el servidor"
                    else -> "Error de red: ${e.code()}"
                },
                statusCode = e.code(),
                data = null,
                bodyString = e.response()?.errorBody()?.string()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar trabajador", e)
            WorkerResponse(
                success = false,
                message = e.message ?: "Error al eliminar trabajador",
                statusCode = 500,
                data = null,
                bodyString = e.stackTraceToString()
            )
        }
    }

    suspend fun registrarTrabajador(request: WorkerRequest): WorkerResponse {
        return try {
            val response = apiService.registrarTrabajador(request)
            if (response.isSuccessful) {
                response.body() ?: WorkerResponse(
                    success = false,
                    message = "Respuesta vacía del servidor",
                    statusCode = response.code(),
                    data = null,
                    bodyString = null
                )
            } else {
                WorkerResponse(
                    success = false,
                    message = "Error al registrar: ${response.code()}",
                    statusCode = response.code(),
                    data = null,
                    bodyString = null
                )
            }
        } catch (e: Exception) {
            WorkerResponse(
                success = false,
                message = e.message ?: "Error al registrar trabajador",
                statusCode = 500,
                data = null,
                bodyString = null
            )
        }
    }

    suspend fun actualizarTrabajador(idTrabajador: Int, request: WorkerRequest): WorkerResponse {
        return try {
            val response = apiService.actualizarTrabajador(idTrabajador, request)
            if (!response.success) {
                Log.w(TAG, "La actualización no fue exitosa: ${response.message}")
            }
            response
        } catch (e: HttpException) {
            Log.e(TAG, "Error HTTP al actualizar: ${e.code()}", e)
            WorkerResponse(
                success = false,
                message = when (e.code()) {
                    404 -> "Trabajador no encontrado"
                    400 -> "Datos inválidos"
                    500 -> "Error en el servidor"
                    else -> "Error de red: ${e.code()}"
                },
                statusCode = e.code(),
                data = null,
                bodyString = null
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar trabajador", e)
            WorkerResponse(
                success = false,
                message = e.message ?: "Error al actualizar trabajador",
                statusCode = 500,
                data = null,
                bodyString = null
            )
        }
    }
} 