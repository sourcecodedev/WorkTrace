package com.upc.worktrace.data.remote

import com.upc.worktrace.data.model.ApiOwner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val clients = mutableMapOf<String, ApiService>()

    fun getClient(owner: String): ApiService {
        return clients.getOrPut(owner.lowercase()) {
            val baseUrl = ApiOwner.fromName(owner)?.baseUrl
                ?: throw IllegalArgumentException("Nombre de desarrollador no válido: $owner")

            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
