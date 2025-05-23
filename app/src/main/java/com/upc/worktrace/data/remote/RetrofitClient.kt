package com.upc.worktrace.data.remote

import com.upc.worktrace.data.model.ApiOwner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val clients = mutableMapOf<String, ApiService>()

    fun getClient(owner: String, customBaseUrl: String? = null): ApiService {
        val key = if (customBaseUrl != null) "$owner-$customBaseUrl" else owner.lowercase()
        
        return clients.getOrPut(key) {
            val baseUrl = customBaseUrl ?: (ApiOwner.fromName(owner)?.baseUrl
                ?: throw IllegalArgumentException("Nombre de desarrollador no válido: $owner"))

            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
