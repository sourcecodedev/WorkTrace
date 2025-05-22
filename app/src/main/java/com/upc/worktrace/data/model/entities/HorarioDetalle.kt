package com.upc.worktrace.data.model.entities

data class HorarioDetalle(
    val idDetalle: Int,
    val idHorarioAsignacion: Int,
    val diaSemana: Int,
    val horaEntrada: String,
    val horaSalida: String
)
