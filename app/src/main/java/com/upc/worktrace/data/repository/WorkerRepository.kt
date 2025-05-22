package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.entities.Trabajador
import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.WorkerResponse
import com.upc.worktrace.data.remote.RetrofitClient

class WorkerRepository (owner: String) {

    private val api = RetrofitClient.getClient(owner)
    suspend fun RegistarTrajador(trabajador: Trabajador): WorkerResponse {
        val request = WorkerRequest(trabajador)
        return api.registrarWorker(request)

    }
}