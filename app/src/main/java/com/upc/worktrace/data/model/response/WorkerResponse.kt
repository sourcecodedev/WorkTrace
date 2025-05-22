package com.upc.worktrace.data.model.response

import com.upc.worktrace.data.model.entities.Trabajador

data class WorkerResponse (  val success: Boolean,
                             val message: String,
                             val trab: Trabajador?)

