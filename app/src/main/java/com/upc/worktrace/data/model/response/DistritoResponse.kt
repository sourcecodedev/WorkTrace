package com.upc.worktrace.data.model.response

import com.google.gson.annotations.SerializedName
import com.upc.worktrace.data.model.entities.DistritoTrabajo

data class DistritoResponse(
    val statusCode: Int,
    val success: Boolean = false,
    val message: String = "",
    val distrito: List<DistritoTrabajo> = emptyList(),
    val headers: Map<String, String> = emptyMap(),
    @SerializedName("body")
    val bodyString: String? = null // Para capturar el body como string cuando viene anidado
)