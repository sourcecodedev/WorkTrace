package com.upc.worktrace.data.model.response

import com.google.gson.annotations.SerializedName

data class DistritoResponse(
    @SerializedName("statusCode")
    val statusCode: Int,
    
    @SerializedName("body")
    val bodyString: String,
    
    @SerializedName("headers")
    val headers: Map<String, String>
)

data class DistritoBodyResponse(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("message")
    val message: String?,
    
    @SerializedName("distrito")
    val distrito: List<DistritoItem>
)

data class DistritoItem(
    @SerializedName("id_distrito_trabajo")
    val idDistritoTrabajo: Int,
    
    @SerializedName("nombre")
    val nombre: String
)