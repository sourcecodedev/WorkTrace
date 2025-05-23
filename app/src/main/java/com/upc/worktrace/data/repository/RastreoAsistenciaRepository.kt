package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.response.RastreoAsistenciaResponse
import com.upc.worktrace.data.remote.RetrofitClient

class RastreoAsistenciaRepository (owner: String) {

    private val api = RetrofitClient.getClient(owner)
    suspend fun mostrarRastreoAsistencia(asistenciaId: Int): RastreoAsistenciaResponse {
        return api.mostrarRastreo(asistenciaId)

    }
}