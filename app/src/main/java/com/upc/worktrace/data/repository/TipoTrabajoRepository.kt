package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.response.TipoContratoResponse
import com.upc.worktrace.data.remote.RetrofitClient

class TipoTrabajoRepository(owner: String, baseUrl: String? = null) {
    private val api = RetrofitClient.getClient(owner, baseUrl)

    suspend fun listarTiposTrabajo(): TipoContratoResponse {
        return api.listarTiposTrabajo()
    }
} 