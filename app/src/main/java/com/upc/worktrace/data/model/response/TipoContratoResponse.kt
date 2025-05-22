package com.upc.worktrace.data.model.response

import com.upc.worktrace.data.model.entities.TipoContrato

data class TipoContratoResponse(
    val success: Boolean,
    val message: String,
    val contrato: List<TipoContrato>?
)