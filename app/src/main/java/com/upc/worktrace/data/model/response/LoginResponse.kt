package com.upc.worktrace.data.model.response

import com.upc.worktrace.data.model.entities.Usuario

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val user: Usuario?
)