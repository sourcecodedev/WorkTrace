package com.upc.worktrace.data.model

data class User(
    val id: Int,
    val username: String,
    val password: String,
    val role: String
)