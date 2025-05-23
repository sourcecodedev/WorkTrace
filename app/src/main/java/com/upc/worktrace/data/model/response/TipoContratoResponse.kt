package com.upc.worktrace.data.model.response

import com.google.gson.annotations.SerializedName
import com.upc.worktrace.data.model.entities.TipoContrato

data class TipoContratoResponse(
    val statusCode: Int,
    val success: Boolean = false,
    val message: String = "",
    @SerializedName("contrato")
    val contrato: List<TipoContrato> = emptyList(),
    val headers: Map<String, String> = emptyMap(),
    @SerializedName("body")
    val bodyString: String? = null
)