package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.request.MarcarSalidaRequest
import com.upc.worktrace.data.model.response.MarcarSalidaResponse
import com.upc.worktrace.data.remote.RetrofitClient

class MarcarSalidaRepository(owner: String, baseUrl: String? = null) {

    private val api = RetrofitClient.getClient(owner, baseUrl)
    
    suspend fun RegistrarMarcarSalida(marcarSalida: MarcarSalidaRequest): MarcarSalidaResponse {
        return api.registrarSalida(marcarSalida)
    }
}