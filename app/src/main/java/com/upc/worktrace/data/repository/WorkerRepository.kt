package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.WorkerResponse
import com.upc.worktrace.data.remote.RetrofitClient

class WorkerRepository(owner: String, baseUrl: String? = null) {
    private val apiService = RetrofitClient.getClient(owner, baseUrl)

    suspend fun registrarTrabajador(request: WorkerRequest): WorkerResponse {
        return apiService.registrarTrabajador(request)
    }

    suspend fun obtenerTodosTrabajadores(): WorkerResponse {
        return apiService.obtenerTodosTrabajadores()
    }

    suspend fun obtenerTrabajadorPorId(trabajadorId: Int): WorkerResponse {
        return apiService.obtenerTrabajadorPorId(trabajadorId)
    }

    suspend fun actualizarTrabajador(trabajadorId: Int, request: WorkerRequest): WorkerResponse {
        return apiService.actualizarTrabajador(trabajadorId, request)
    }

    suspend fun eliminarTrabajador(trabajadorId: Int): WorkerResponse {
        return apiService.eliminarTrabajador(trabajadorId)
    }
} 