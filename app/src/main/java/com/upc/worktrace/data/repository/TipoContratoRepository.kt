package com.upc.worktrace.data.repository

import android.util.Log
import com.google.gson.GsonBuilder
import com.upc.worktrace.data.model.response.TipoContratoResponse
import com.upc.worktrace.data.remote.RetrofitClient
import okhttp3.Headers

class TipoContratoRepository(owner: String, baseUrl: String? = null) {
    private val api = RetrofitClient.getClient(owner, baseUrl)
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val TAG = "TipoContratoRepository"

    suspend fun listarTiposContrato(): TipoContratoResponse {
        try {
            // Log de la petición
            Log.d(TAG, "\n┌────── Request ──────")
            Log.d(TAG, "│ URL Base: ${RetrofitClient.getBaseUrl()}")
            Log.d(TAG, "│ Owner: ")
            
            val response = api.listarTiposContrato()
            
            // Log de la respuesta
            Log.d(TAG, "\n┌────── Response ──────")
            Log.d(TAG, "│ Status Code: ${response.code()}")
            Log.d(TAG, "│ Message: ${response.message()}")
            Log.d(TAG, "│ Headers:")
            logHeaders(response.headers())
            
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d(TAG, "│ Body: ${gson.toJson(responseBody)}")
                
                responseBody?.let { apiResponse ->
                    if (apiResponse.bodyString != null) {
                        try {
                            Log.d(TAG, "│ Parsing body string: ${apiResponse.bodyString}")
                            return gson.fromJson(apiResponse.bodyString, TipoContratoResponse::class.java)
                        } catch (e: Exception) {
                            Log.e(TAG, "│ Error parsing body: ${e.message}")
                            throw Exception("Error al procesar la respuesta del servidor: ${e.message}")
                        }
                    }
                    return apiResponse
                } ?: throw Exception("No se recibieron datos del servidor")
            } else {
                val errorBody = response.errorBody()?.string()
                Log.e(TAG, "│ Error Body: $errorBody")
                throw Exception("Error ${response.code()} al obtener tipos de contrato: ${response.message()}\nError Body: $errorBody")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error en la petición", e)
            throw e
        }
    }

    private fun logHeaders(headers: Headers) {
        headers.forEach { (name, value) ->
            Log.d(TAG, "│  $name: $value")
        }
    }
} 