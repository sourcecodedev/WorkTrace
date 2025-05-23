package com.upc.worktrace.data.model.request

data class MarcarAsistenciaRequest(
    val id_trabajador: Int,
    val id_horario_asignacion: Int,
    val fecha: String,  // formato: 'YYYY-MM-DD'
    val hora_marcacion: String,  // formato: 'HH:MM:SS'
    val latitud: Double,
    val longitud: Double,
    val ubicacion_texto: String,
    val fuente: String  // 'APP' o 'GPS'
) 