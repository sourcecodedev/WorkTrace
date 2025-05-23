package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.entities.HorarioAsignacion
import com.upc.worktrace.data.model.request.HorarioAsignacionRequest
import com.upc.worktrace.data.model.response.HorarioAsignacionResponse
import com.upc.worktrace.data.remote.RetrofitClient

class HorarioAsignacionRepository(owner: String, baseUrl: String? = null) {

    private val api = RetrofitClient.getClient(owner, baseUrl)
    suspend fun RegistrarHorarioAsignacion(horarioAsignacion: HorarioAsignacion): HorarioAsignacionResponse {
        val request = HorarioAsignacionRequest(horarioAsignacion)
        return api.registrarHorarioAsignacion(request)
    }
}