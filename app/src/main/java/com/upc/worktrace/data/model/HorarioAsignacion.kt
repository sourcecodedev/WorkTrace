package com.upc.worktrace.data.model

data class HorarioAsignacion(
    val idHorarioAsignacion: Int,
    val idTrabajador: Int,
    val idDiaSemana: Int,
    val horaEntrada: String,
    val horaSalida: String,
    val fechaInicio: String,
    val fechaFin: String?
)