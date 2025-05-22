package com.upc.worktrace.data.model.response

import com.upc.worktrace.data.model.entities.DistritoTrabajo
import java.util.List

data class DistritoResponse(
    val success: Boolean,
    val message: String,
    val distrito:  List<DistritoTrabajo>?
)