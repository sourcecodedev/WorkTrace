package com.upc.worktrace.data.model.response

import com.google.gson.annotations.SerializedName
import com.upc.worktrace.data.model.entities.TipoContrato
import com.upc.worktrace.data.model.entities.Trabajador

data class WorkerResponse(
    val success: Boolean,
    val message: String,
    val data: Trabajador? = null,
    val workers: List<Trabajador>? = null,
    val statusCode: Int,
    @SerializedName("Trabajador")
    val contrato: List<Trabajador> = emptyList(),
    val headers: Map<String, String> = emptyMap(),
    @SerializedName("body")
    val bodyString: String? = null
)

