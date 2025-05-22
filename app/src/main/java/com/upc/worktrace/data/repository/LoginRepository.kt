package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.request.LoginRequest
import com.upc.worktrace.data.model.response.LoginResponse
import com.upc.worktrace.data.remote.RetrofitClient

class LoginRepository(owner: String) {

    private val api = RetrofitClient.getClient(owner)
    
    suspend fun login(username: String, password: String): LoginResponse {
        val request = LoginRequest(username, password)
        return if (username == "admin" && password == "admin") {
            api.login(request)
        } else {
            api.loginWorker(request)
        }
    }
}