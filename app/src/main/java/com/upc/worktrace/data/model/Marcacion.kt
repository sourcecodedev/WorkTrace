package com.upc.worktrace.data.model

data class Marcacion(
    val idMarcacion: Int,
    val idTrabajador: Int,
    val tipoMarcacion: String, // Entrada o Salida
    val fechaHora: String,
    val ubicacion: String?
)