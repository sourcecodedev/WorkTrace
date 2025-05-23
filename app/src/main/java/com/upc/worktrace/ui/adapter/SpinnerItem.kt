package com.upc.worktrace.ui.adapter

data class SpinnerItem<T>(
    val id: T,
    val nombre: String
) {
    override fun toString(): String = nombre
} 