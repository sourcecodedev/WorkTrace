package com.upc.worktrace.data.model.entities

data class ReporteGenerado(
    val idReporteGenerado: Int,
    val idTipoReporte: Int,
    val idTrabajador: Int?,
    val fechaInicio: String,
    val fechaFin: String,
    val fechaGeneracion: String
)
