package com.upc.worktrace.data.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para representar a un trabajador en la aplicación.
 */
data class Trabajador(
    @SerializedName("id_trabajador")
    val idTrabajador: Int,

    @SerializedName("id_usuario")
    val idUsuario: Int,

    @SerializedName("nombres_apellidos")
    val nombres: String,

    @SerializedName("puesto")
    val puesto: String,

    @SerializedName("jefe_inmediato")
    val jefeInmediato: String,

    @SerializedName("id_tipo_contrato")
    val idTipoContrato: String,

    @SerializedName("direccion")
    val direccion: String,

    @SerializedName("telefono")
    val telefono: String,

    @SerializedName("id_distrito_trabajo")
    val idDistritoTrabajo: Int,

    @SerializedName("estado")
    val estado: Int,

    @SerializedName("fecha_registro")
    val fechaRegistro: String,

    @SerializedName("id_usuario_creador")
    val idUsuarioCreador: Int
)