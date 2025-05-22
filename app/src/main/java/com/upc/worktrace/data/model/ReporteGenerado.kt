package com.upc.worktrace.data.model

data class ReporteGenerado(
    val idReporteGenerado: Int,
    val idTipoReporte: Int,
    val idTrabajador: Int?,
    val fechaInicio: String,
    val fechaFin: String,
    val fechaGeneracion: String
)
