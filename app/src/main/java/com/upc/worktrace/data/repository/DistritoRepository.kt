package com.upc.worktrace.data.repository

import android.util.Log
import com.upc.worktrace.data.model.response.DistritoResponse
import com.upc.worktrace.data.remote.RetrofitClient

class DistritoRepository(ownerName: String, baseUrl: String? = null) {
    private val TAG = "DistritoRepository"
    private val apiService = RetrofitClient.getClient(ownerName, baseUrl)

    suspend fun listarDistritos(): DistritoResponse {
        Log.d(TAG, "Solicitando lista de distritos al servidor")
        val response = apiService.listarDistritos()
        
        // Imprimir la respuesta HTTP completa
        Log.d(TAG, """
            |┌────── Respuesta HTTP Raw ──────
            |├── Successful: ${response.isSuccessful}
            |├── Code: ${response.code()}
            |├── Message: ${response.message()}
            |├── Headers: ${response.headers()}
            |├── Raw Response: ${response.raw()}
            |└── Body: ${response.body()}
        """.trimMargin())
        
        if (!response.isSuccessful) {
            val errorBody = response.errorBody()?.string()
            Log.e(TAG, """
                |┌────── Error Response ──────
                |├── Code: ${response.code()}
                |├── Message: ${response.message()}
                |└── Error Body: $errorBody
            """.trimMargin())
            throw Exception("Error al obtener distritos: ${response.code()} - ${response.message()}")
        }

        val distritoResponse = response.body()
        if (distritoResponse == null) {
            Log.e(TAG, "Respuesta vacía del servidor")
            throw Exception("Respuesta vacía del servidor")
        }

        Log.d(TAG, """
            |┌────── Respuesta Procesada ──────
            |├── Status Code: ${distritoResponse.statusCode}
            |├── Body: ${distritoResponse.bodyString}
            |└── Headers: ${distritoResponse.headers}
        """.trimMargin())

        return distritoResponse
    }
}
