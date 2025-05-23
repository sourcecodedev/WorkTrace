package com.upc.worktrace.data.remote

import android.util.Log
import com.upc.worktrace.data.model.ApiOwner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val TAG = "RetrofitClient"
    private var token: String? = null
    private var apiOwner: ApiOwner = ApiOwner.PROD
    private var customBaseUrl: String? = null

    fun getBaseUrl(): String = customBaseUrl ?: apiOwner.baseUrl

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        
        val requestBuilder = originalRequest.newBuilder().apply {
            header("Content-Type", "application/json")
            header("Accept", "application/json")
            
            // Solo agregamos el token si no es el owner GENERAL
            if (apiOwner != ApiOwner.GENERAL && !token.isNullOrEmpty()) {
                header("Authorization", "Bearer $token")
                Log.d(TAG, "Agregando token de autorización: Bearer $token")
            } else {
                Log.d(TAG, "No se agrega token de autorización para ${apiOwner.name}")
            }
        }

        val request = requestBuilder.build()
        Log.d(TAG, "\n┌────── Request Headers ──────")
        request.headers.forEach { (name, value) ->
            Log.d(TAG, "│ $name: $value")
        }
        
        chain.proceed(request)
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    fun getClient(ownerName: String, baseUrl: String? = null): ApiService {
        // Guardamos la URL base personalizada si se proporciona
        customBaseUrl = baseUrl
        
        // Determinamos el ApiOwner según el caso
        apiOwner = when {
            baseUrl != null -> ApiOwner.entries.find { it.baseUrl == baseUrl } ?: ApiOwner.PROD
            else -> ApiOwner.fromName(ownerName)
        }
        
        token = ownerName // Guardamos el token/owner para usarlo en las peticiones

        Log.d(TAG, """
            |Configurando cliente Retrofit:
            |├── Owner/Token: $ownerName
            |├── ApiOwner: ${apiOwner.name}
            |└── Base URL: ${getBaseUrl()}
        """.trimMargin())

        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
