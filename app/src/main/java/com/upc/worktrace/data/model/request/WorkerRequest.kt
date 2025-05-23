package com.upc.worktrace.data.model.request

import com.google.gson.annotations.SerializedName

data class WorkerRequest(
    @SerializedName("nombres_apellidos")
    val nombres: String,
    
    @SerializedName("puesto")
    val puesto: String,
    
    @SerializedName("jefe_inmediato")
    val jefeInmediato: String,
    
    @SerializedName("id_tipo_contrato")
    val idTipoContrato: Int,
    
    @SerializedName("direccion")
    val direccion: String,
    
    @SerializedName("telefono")
    val telefono: String,
    
    @SerializedName("id_distrito_trabajo")
    val idDistritoTrabajo: Int,
    
    @SerializedName("id_usuario_creador")
    val idUsuarioCreador: Int = 1,
    
    @SerializedName("estado")
    val estado: Int = 1
)