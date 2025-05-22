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
<<<<<<< Updated upstream
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
=======
import retrofit2.http.*
>>>>>>> Stashed changes

interface ApiService {

    @POST("/api/login/admin")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("/api/login/worker")
    suspend fun loginWorker(@Body request: LoginRequest): LoginResponse

<<<<<<< Updated upstream
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

    @POST("trabajadores")
    suspend fun registrarTrabajador(@Body request: WorkerRequest): WorkerResponse

    @GET("trabajadores")
    suspend fun obtenerTodosTrabajadores(): WorkerResponse

    @GET("trabajadores/{id}")
    suspend fun obtenerTrabajadorPorId(@Path("id") trabajadorId: Int): WorkerResponse

    @PUT("trabajadores/{id}")
=======
    // Endpoints de Trabajador
    @POST("/api/worker")
    suspend fun registrarTrabajador(@Body request: WorkerRequest): WorkerResponse

    @GET("/api/worker")
    suspend fun obtenerTodosTrabajadores(): WorkerResponse

    @GET("/api/worker/{id}")
    suspend fun obtenerTrabajadorPorId(@Path("id") trabajadorId: Int): WorkerResponse

    @PUT("/api/worker/{id}")
>>>>>>> Stashed changes
    suspend fun actualizarTrabajador(
        @Path("id") trabajadorId: Int,
        @Body request: WorkerRequest
    ): WorkerResponse

<<<<<<< Updated upstream
    @DELETE("trabajadores/{id}")
    suspend fun eliminarTrabajador(@Path("id") trabajadorId: Int): WorkerResponse

    @GET("api/tipotrabajo")
    suspend fun listarTiposTrabajo(): TipoContratoResponse

    @GET("api/distrito")
    suspend fun listarDistritos(): Response<List<DistritoResponse>>
=======
    @DELETE("/api/worker/{id}")
    suspend fun eliminarTrabajador(@Path("id") trabajadorId: Int): WorkerResponse
>>>>>>> Stashed changes
}