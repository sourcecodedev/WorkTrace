package com.upc.worktrace.data.remote

import com.upc.worktrace.data.model.LoginRequest
import com.upc.worktrace.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/login/admin")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/login/worker")
    suspend fun loginWorker(@Body request: LoginRequest): LoginResponse

}