package com.upc.worktrace.data.model.entities

data class UbicacionRastreo(
    val idUbicacion: Int,
    val idAsistencia: Int,
    val latitud: Double,
    val longitud: Double,
    val fechaHora: String
)