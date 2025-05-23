package com.upc.worktrace.data.model.response

import com.google.gson.annotations.SerializedName
import com.upc.worktrace.data.model.entities.Trabajador

data class WorkerResponse(
    val success: Boolean,
    val message: String? = null,
    val data: List<Trabajador>? = null,
    val statusCode: Int,
    val headers: Map<String, String> = emptyMap(),
    @SerializedName("body")
    val bodyString: String? = null
)

data class WorkerBodyResponse(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("message")
    val message: String? = null,
    
    @SerializedName("data")
    val data: List<Trabajador>? = null
)