package com.upc.worktrace.data.model.entities

import com.google.gson.annotations.SerializedName

data class RastreoUbicacion(
    @SerializedName("id_rastreo")
    val idRastreo: Int = 0,
    
    @SerializedName("id_asistencia")
    val idAsistencia: Int,
    
    @SerializedName("timestamp_registro")
    val timestampRegistro: String,
    
    @SerializedName("latitud")
    val latitud: Double,
    
    @SerializedName("longitud")
    val longitud: Double
) 