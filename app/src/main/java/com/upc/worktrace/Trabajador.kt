package com.upc.worktrace

/**
 * Modelo de datos para representar a un trabajador en la aplicación.
 */
data class Trabajador(
    val id: String,
    val nombre: String,
    val rol: String = "usuario" // Por defecto, los trabajadores son usuarios
) 