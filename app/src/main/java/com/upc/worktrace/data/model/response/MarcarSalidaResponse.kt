package com.upc.worktrace.data.model.response

import com.upc.worktrace.data.model.entities.Marcacion

data class MarcarSalidaResponse(val success: Boolean,
                                val message: String,
                                val marcarSalida: Marcacion?)