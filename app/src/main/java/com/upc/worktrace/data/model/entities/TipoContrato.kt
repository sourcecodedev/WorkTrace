package com.upc.worktrace.data.model.entities

import com.google.gson.annotations.SerializedName

data class TipoContrato(
    @SerializedName("id_tipo_contrato")
    val idTipoContrato: Int,
    val nombre: String
)
