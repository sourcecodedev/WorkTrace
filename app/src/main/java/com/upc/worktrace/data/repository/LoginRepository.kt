package com.upc.worktrace.data.repository

import com.upc.worktrace.data.model.LoginRequest
import com.upc.worktrace.data.model.LoginResponse
import com.upc.worktrace.data.remote.RetrofitClient

class LoginRepository(owner: String) {

    private val api = RetrofitClient.getClient(owner)
    suspend fun login(username: String, password: String): LoginResponse {
        val request = LoginRequest(username, password)
        if (username == "admin" && password == "admin") {
            return api.login(request)
        }

        return api.loginWorker(request)

    }

}