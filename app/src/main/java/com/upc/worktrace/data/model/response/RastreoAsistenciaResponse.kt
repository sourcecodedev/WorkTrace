package com.upc.worktrace.data.model.response

import com.upc.worktrace.data.model.entities.UbicacionRastreo

data class RastreoAsistenciaResponse(val success: Boolean,
                                     val message: String,
                                     val rastreo: UbicacionRastreo?)
