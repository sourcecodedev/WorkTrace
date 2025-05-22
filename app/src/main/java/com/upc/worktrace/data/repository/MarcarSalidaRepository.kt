package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.entities.Marcacion
import com.upc.worktrace.data.model.request.MarcarSalidaRequest
import com.upc.worktrace.data.model.response.MarcarSalidaResponse
import com.upc.worktrace.data.remote.RetrofitClient

class MarcarSalidaRepository (owner: String) {

    private val api = RetrofitClient.getClient(owner)
    suspend fun RegistrarMarcarSalida(marcarSalida: Marcacion): MarcarSalidaResponse {
        val request = MarcarSalidaRequest(marcarSalida)
        return api.registrarSalida(request)

    }
}