package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.response.TipoContratoResponse
import com.upc.worktrace.data.remote.RetrofitClient

class TipoTrabajoRepository(owner: String) {
    private val api = RetrofitClient.getClient(owner)

    suspend fun listarTiposTrabajo(): TipoContratoResponse {
        return api.listarTiposTrabajo()
    }
} 