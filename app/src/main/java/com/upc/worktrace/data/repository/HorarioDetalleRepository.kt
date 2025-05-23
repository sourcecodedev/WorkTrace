package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.request.HorarioDetalleRequest
import com.upc.worktrace.data.model.response.HorarioDetalleResponse
import com.upc.worktrace.data.remote.RetrofitClient

class HorarioDetalleRepository (owner: String) {

    private val api = RetrofitClient.getClient(owner)
    suspend fun RegistrarHorarioDetalle(horarioDetalle: HorarioDetalleRequest): HorarioDetalleResponse {
        return api.registrarHorarioDetalle(horarioDetalle)

    }
}