package com.upc.worktrace.data.model.response

import com.upc.worktrace.data.model.entities.HorarioAsignacion


data class AsistentResponse(  val success: Boolean,
                              val message: String,
                              val horarioAsignacion: HorarioAsignacion?)
