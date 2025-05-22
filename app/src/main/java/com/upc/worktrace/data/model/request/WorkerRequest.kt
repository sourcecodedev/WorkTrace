package com.upc.worktrace.data.model.request

data class WorkerRequest(
    val nombres: String,
    val puesto: String,
    val jefeInmediato: String,
    val idTipoContrato: Int,
    val direccion: String,
    val telefono: String,
    val idDistritoTrabajo: Int,
    val idUsuario: Int? = null, // Opcional, usado para actualizaciones
    val password: String? = null // Opcional, usado solo para creación
)