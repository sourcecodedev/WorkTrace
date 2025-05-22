package com.upc.worktrace.data.model.entities

data class Asistencia(
    val idAsistencia: Int,
    val idTrabajador: Int,
    val idHorarioAsignacion: Int,
    val fecha: String,
    val horaEntrada: String,
    val horaSalida: String?,
    val estado: String
)