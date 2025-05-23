package com.upc.worktrace.data.remote

import com.upc.worktrace.data.model.request.HorarioAsignacionRequest
import com.upc.worktrace.data.model.request.HorarioDetalleRequest
import com.upc.worktrace.data.model.request.LoginRequest
import com.upc.worktrace.data.model.request.MarcarAsistenciaRequest
import com.upc.worktrace.data.model.request.MarcarSalidaRequest
import com.upc.worktrace.data.model.request.RastreoAsistenciaRequest
import com.upc.worktrace.data.model.request.RastreoUbicacionRequest
import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.DistritoResponse
import com.upc.worktrace.data.model.response.HorarioAsignacionResponse
import com.upc.worktrace.data.model.response.HorarioDetalleResponse
import com.upc.worktrace.data.model.response.LoginResponse
import com.upc.worktrace.data.model.response.MarcarAsistenciaResponse
import com.upc.worktrace.data.model.response.MarcarSalidaResponse
import com.upc.worktrace.data.model.response.RastreoAsistenciaResponse
import com.upc.worktrace.data.model.response.RastreoUbicacionResponse
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

    // Gestión de Trabajadores
    @GET("v1/Trabajador")
    suspend fun obtenerTodosTrabajadores(): WorkerResponse

    @GET("/api/worker/{id}")
    suspend fun obtenerTrabajadorPorId(@Path("id") trabajadorId: Int): WorkerResponse

    @PUT("/api/worker/{id}")
    suspend fun actualizarTrabajador(
        @Path("id") trabajadorId: Int,
        @Body request: WorkerRequest
    ): WorkerResponse

    @DELETE("v1/Trabajador/{id_trabajador}")
    suspend fun eliminarTrabajador(@Path("id_trabajador") trabajadorId: Int): WorkerResponse

    // Gestión de Horarios
    @POST("/v1/horario-asignacion")
    suspend fun registrarHorarioAsignacion(@Body request: HorarioAsignacionRequest): HorarioAsignacionResponse

    @POST("/v1/horario-detalle")
    suspend fun registrarHorarioDetalle(@Body request: HorarioDetalleRequest): HorarioDetalleResponse

    @POST("/v1/marcar-salida")
    suspend fun registrarSalida(@Body request: MarcarSalidaRequest): MarcarSalidaResponse

    @GET("/v1/rastreo-asistencia")
    suspend fun mostrarRastreo(@Path("idAsistencia") idAsistencia: Int): RastreoAsistenciaResponse

    @POST("/v1/marcacion")
    suspend fun registrarMarcacionAsistencia(@Body request: MarcarAsistenciaRequest): MarcarAsistenciaResponse

    @POST("v1/rastreoubicacion")
    suspend fun registrarRastreoUbicacion(@Body request: RastreoUbicacionRequest): RastreoUbicacionResponse

    // Endpoints de consulta
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @GET("v1/tipocontrato")
    suspend fun listarTiposContrato(): Response<TipoContratoResponse>

    @GET("v1/DistritoTrabajo")
    suspend fun listarDistritos(): Response<DistritoResponse>

    @POST("v1/Trabajador")
    suspend fun registrarTrabajador(@Body request: WorkerRequest): Response<WorkerResponse>
}