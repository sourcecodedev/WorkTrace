package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.response.DistritoResponse
import com.upc.worktrace.data.remote.RetrofitClient

class DistritoRepository(owner: String) {
    private val api = RetrofitClient.getClient(owner)

    suspend fun listarDistritos(): List<DistritoResponse> {
        val response = api.listarDistritos()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            throw Exception("Error ${response.code()} al obtener distritos: ${response.message()}")
        }
    }
}
