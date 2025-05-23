package com.upc.worktrace.data.repository

import com.google.gson.GsonBuilder
import com.upc.worktrace.data.model.response.DistritoResponse
import com.upc.worktrace.data.remote.RetrofitClient

class DistritoRepository(owner: String, baseUrl: String? = null) {
    private val api = RetrofitClient.getClient(owner, baseUrl)
    private val gson = GsonBuilder().setPrettyPrinting().create()

    suspend fun listarDistritos(): DistritoResponse {
        val response = api.listarDistritos()
        
        if (response.isSuccessful) {
            val responseBody = response.body()
            responseBody?.let { apiResponse ->
                if (apiResponse.bodyString != null) {
                    return gson.fromJson(apiResponse.bodyString, DistritoResponse::class.java)
                }
                return apiResponse
            } ?: throw Exception("No se recibieron datos del servidor")
        } else {
            throw Exception("Error ${response.code()} al obtener distritos: ${response.message()}")
        }
    }
}
