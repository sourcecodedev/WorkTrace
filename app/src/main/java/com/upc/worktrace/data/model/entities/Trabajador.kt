package com.upc.worktrace.data.model.entities

/**
 * Modelo de datos para representar a un trabajador en la aplicación.
 */
data class Trabajador(

    val rol: String = "usuario", // Por defecto, los trabajadores son usuarios
    val idTrabajador: Int,
    val idUsuario: Int,
    val nombres: String,
    val puesto: String,
    val jefeInmediato: String,
    val idTipoContrato: Int,
    val direccion: String,
    val telefono: String,
    val idDistritoTrabajo: Int
)