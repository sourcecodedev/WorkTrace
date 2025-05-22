package com.upc.worktrace.data.model.entities

data class BitacoraTrabajador(
    val idBitacora: Int,
    val idTrabajador: Int,
    val fechaHora: String,
    val accion: String,
    val observacion: String?
)
