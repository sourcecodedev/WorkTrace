package com.upc.worktrace.data.remote

import com.upc.worktrace.data.model.request.HorarioAsignacionRequest
import com.upc.worktrace.data.model.request.HorarioDetalleRequest
import com.upc.worktrace.data.model.request.LoginRequest
import com.upc.worktrace.data.model.request.MarcarSalidaRequest
import com.upc.worktrace.data.model.request.RastreoAsistenciaRequest
import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.DistritoResponse
import com.upc.worktrace.data.model.response.HorarioAsignacionResponse
import com.upc.worktrace.data.model.response.HorarioDetalleResponse
import com.upc.worktrace.data.model.response.LoginResponse
import com.upc.worktrace.data.model.response.MarcarSalidaResponse
import com.upc.worktrace.data.model.response.RastreoAsistenciaResponse
import com.upc.worktrace.data.model.response.TipoContratoResponse
import com.upc.worktrace.data.model.response.WorkerResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Autenticación
    @POST("/api/login/admin")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/login/worker")
    suspend fun loginWorker(@Body request: LoginRequest): LoginResponse

    // Gestión de Workers
    @POST("/api/Worker/registrar")
    suspend fun registrarWorker(@Body request: WorkerRequest): WorkerResponse

    @GET("/api/worker")
    suspend fun obtenerTodosTrabajadores(): WorkerResponse

    @GET("/api/worker/{id}")
    suspend fun obtenerTrabajadorPorId(@Path("id") trabajadorId: Int): WorkerResponse

    @PUT("/api/worker/{id}")
    suspend fun actualizarTrabajador(
        @Path("id") trabajadorId: Int,
        @Body request: WorkerRequest
    ): WorkerResponse

    @DELETE("/api/worker/{id}")
    suspend fun eliminarTrabajador(@Path("id") trabajadorId: Int): WorkerResponse

    // Gestión de Horarios
    @POST("/v1/horario-asignacion")
    suspend fun registrarHorarioAsignacion(@Body request: HorarioAsignacionRequest): HorarioAsignacionResponse

    @POST("/v1/horario-detalle")
    suspend fun registrarHorarioDetalle(@Body request: HorarioDetalleRequest): HorarioDetalleResponse

    @POST("/v1/marcar-salida")
    suspend fun registrarSalida(@Body request: MarcarSalidaRequest): MarcarSalidaResponse

    @POST("/v1/rastreo-asistencia")
    suspend fun mostrarRastreo(@Body request: RastreoAsistenciaRequest): RastreoAsistenciaResponse

    // Endpoints de consulta
    @GET("api/tipotrabajo")
    suspend fun listarTiposTrabajo(): TipoContratoResponse

    @GET("api/distrito")
    suspend fun listarDistritos(): Response<List<DistritoResponse>>
}