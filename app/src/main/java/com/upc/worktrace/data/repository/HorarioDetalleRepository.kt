package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.entities.HorarioDetalle
import com.upc.worktrace.data.model.request.HorarioDetalleRequest
import com.upc.worktrace.data.model.response.HorarioDetalleResponse
import com.upc.worktrace.data.remote.RetrofitClient

class HorarioDetalleRespository (owner: String) {

    private val api = RetrofitClient.getClient(owner)
    suspend fun RegistrarHorarioDetalle(horarioDetalle: HorarioDetalle): HorarioDetalleResponse {
        val request = HorarioDetalleRequest(horarioDetalle)
        return api.registrarHorarioDetalle(request)

    }
}