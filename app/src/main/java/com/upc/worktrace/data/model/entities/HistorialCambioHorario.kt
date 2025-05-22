package com.upc.worktrace.data.model.entities

data class HistorialCambioHorario(
    val idHistorial: Int,
    val idHorarioAsignacion: Int,
    val fechaCambio: String,
    val motivo: String
)
