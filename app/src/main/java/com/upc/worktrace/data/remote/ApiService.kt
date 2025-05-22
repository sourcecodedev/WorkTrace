package com.upc.worktrace.data.remote

import com.upc.worktrace.data.model.entities.HorarioAsignacion
import com.upc.worktrace.data.model.request.HorarioAsignacionRequest
import com.upc.worktrace.data.model.request.HorarioDetalleRequest
import com.upc.worktrace.data.model.request.LoginRequest
import com.upc.worktrace.data.model.request.MarcarSalidaRequest
import com.upc.worktrace.data.model.request.RastreoAsistenciaRequest
import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.HorarioAsignacionResponse
import com.upc.worktrace.data.model.response.HorarioDetalleResponse
import com.upc.worktrace.data.model.response.LoginResponse
import com.upc.worktrace.data.model.response.MarcarSalidaResponse
import com.upc.worktrace.data.model.response.RastreoAsistenciaResponse
import com.upc.worktrace.data.model.response.WorkerResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("/api/login/admin")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/login/worker")
    suspend fun loginWorker(@Body request: LoginRequest): LoginResponse

    @POST("/api/Worker/registrar")
    suspend fun registrarWorker(@Body request: WorkerRequest): WorkerResponse

    @POST("/v1/horario-asignacion")
    suspend fun registrarHorarioAsignacion(@Body request: HorarioAsignacionRequest): HorarioAsignacionResponse

    @POST("/v1/horario-detalle")
    suspend fun registrarHorarioDetalle(@Body request: HorarioDetalleRequest): HorarioDetalleResponse

    @POST("/v1/marcar-salida")
    suspend fun registrarSalida(@Body request: MarcarSalidaRequest): MarcarSalidaResponse

    @POST("/v1/rastreo-asistencia")
    suspend fun mostrarRastreo(@Body request: RastreoAsistenciaRequest): RastreoAsistenciaResponse
}