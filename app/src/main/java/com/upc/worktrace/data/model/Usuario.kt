package com.upc.worktrace.data.model

data class Usuario(
    val id: Int,
    val username: String,
    val password: String,
    val role: String,
    val idUsuario: Int,
    val idTipoUsuario: Int,
    val estado: Boolean,
    val fechaCreacion: String
)