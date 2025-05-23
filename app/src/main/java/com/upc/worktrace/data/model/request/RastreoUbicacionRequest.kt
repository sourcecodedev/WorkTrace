package com.upc.worktrace.data.model.request

import com.google.gson.annotations.SerializedName

data class RastreoUbicacionRequest(
    @SerializedName("id_asistencia")
    val idAsistencia: Int = 1, // Por defecto 1 como solicitado
    
    @SerializedName("timestamp_registro")
    val timestampRegistro: String,
    
    @SerializedName("latitud")
    val latitud: Double,
    
    @SerializedName("longitud")
    val longitud: Double
) 