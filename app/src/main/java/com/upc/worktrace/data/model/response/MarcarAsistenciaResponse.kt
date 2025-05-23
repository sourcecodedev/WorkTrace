package com.upc.worktrace.data.model.response

import com.google.gson.annotations.SerializedName

data class MarcarAsistenciaResponse(
    val success: Boolean,
    val message: String,
    val statusCode: Int,
    @SerializedName("body")
    val bodyString: String? = null,
    val headers: Map<String, String> = emptyMap()
)

data class MarcarAsistenciaBodyResponse(
    @SerializedName("success")
    val success: Boolean,
    
    @SerializedName("message")
    val message: String?,
    
    @SerializedName("data")
    val data: MarcarAsistenciaData? = null
)

data class MarcarAsistenciaData(
    @SerializedName("id_asistencia")
    val idAsistencia: Int,
    
    @SerializedName("fecha_marcacion")
    val fechaMarcacion: String,
    
    @SerializedName("hora_marcacion")
    val horaMarcacion: String,
    
    @SerializedName("ubicacion")
    val ubicacion: String,
    
    @SerializedName("estado")
    val estado: String
) 