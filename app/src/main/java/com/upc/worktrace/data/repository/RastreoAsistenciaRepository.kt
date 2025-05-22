package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.entities.UbicacionRastreo
import com.upc.worktrace.data.model.request.RastreoAsistenciaRequest
import com.upc.worktrace.data.model.response.RastreoAsistenciaResponse
import com.upc.worktrace.data.remote.RetrofitClient

class RastreoAsistenciaRepository (owner: String) {

    private val api = RetrofitClient.getClient(owner)
    suspend fun mostrarRastreoAsistencia(rastreoAsistencia: UbicacionRastreo): RastreoAsistenciaResponse {
        val request = RastreoAsistenciaRequest(rastreoAsistencia)
        return api.mostrarRastreo(request)

    }
}