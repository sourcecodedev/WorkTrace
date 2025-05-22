package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.entities.HorarioAsignacion
import com.upc.worktrace.data.model.request.HorarioAsignacionRequest
import com.upc.worktrace.data.model.response.HorarioAsignacionResponse
import com.upc.worktrace.data.remote.RetrofitClient

class HorarioAsignacionRepository(owner: String) {

    private val api = RetrofitClient.getClient(owner)
    suspend fun RegistrarHorarioAsignacion(horarioAsignacion: HorarioAsignacion): HorarioAsignacionResponse {
        val request = HorarioAsignacionRequest(horarioAsignacion)
        return api.registrarHorarioAsignacion(request)

    }
}