package com.upc.worktrace.data.model.response

import com.upc.worktrace.data.model.entities.HorarioDetalle

data class HorarioDetalleResponse(val success: Boolean,
                                  val message: String,
                                  val horarioDetalle: HorarioDetalle?)
