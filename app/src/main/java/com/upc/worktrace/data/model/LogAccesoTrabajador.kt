package com.upc.worktrace.data.model

data class LogAccesoTrabajador(
    val idLog: Int,
    val idTrabajador: Int,
    val fechaHoraAcceso: String,
    val exito: Boolean,
    val ipAcceso: String?
)