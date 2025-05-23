package com.upc.worktrace.data.model.request

data class HorarioDetalleRequest (
    val idHorarioAsignacion: String,
    val diaSemana: String,
    val horaEntrada: String,
    val horaSalida: String,
)