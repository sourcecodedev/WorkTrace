package com.upc.worktrace.data.model.response

import com.google.gson.annotations.SerializedName
import com.upc.worktrace.data.model.entities.RastreoUbicacion

data class RastreoUbicacionResponse(
    val success: Boolean,
    val message: String,
    val statusCode: Int,
    @SerializedName("body")
    val bodyString: String? = null,
    val headers: Map<String, String> = emptyMap()
)

data class RastreoUbicacionBodyResponse(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("message")
    val message: String?,
    
    @SerializedName("data")
    val data: RastreoUbicacion? = null
) 