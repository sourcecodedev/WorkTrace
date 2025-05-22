package com.upc.worktrace.data.model

data class BitacoraTrabajador(
    val idBitacora: Int,
    val idTrabajador: Int,
    val fechaHora: String,
    val accion: String,
    val observacion: String?
)
