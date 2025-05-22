package com.upc.worktrace.data.model

data class UbicacionRastreo(
    val idUbicacion: Int,
    val idAsistencia: Int,
    val latitud: Double,
    val longitud: Double,
    val fechaHora: String
)