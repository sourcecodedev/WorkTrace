package com.upc.worktrace.data.model.request

import com.upc.worktrace.data.model.entities.Marcacion

data class MarcarSalidaRequest (
    val idAsistencia: String,
    val tipoMarcacion: String,
    val fechaMarcacion: String,
    val horaMarcacion: String,
    val latitud: String,
    val longitud: String,
    val ubicacion: String,
    val fuente: String,
)