package com.upc.worktrace.data.remote

import com.upc.worktrace.data.model.request.LoginRequest
import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.LoginResponse
import com.upc.worktrace.data.model.response.WorkerResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/login/admin")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/login/worker")
    suspend fun loginWorker(@Body request: LoginRequest): LoginResponse

    @POST("/api/Worker/registrar")
    suspend fun registrarWorker(@Body request: WorkerRequest): WorkerResponse
}