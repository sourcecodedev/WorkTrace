package com.upc.worktrace.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.entities.Trabajador
import com.upc.worktrace.data.model.entities.UbicacionRastreo
import com.upc.worktrace.data.model.request.HorarioDetalleRequest
import com.upc.worktrace.data.model.request.MarcarSalidaRequest
import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.HorarioDetalleResponse
import com.upc.worktrace.data.model.response.MarcarSalidaResponse
import com.upc.worktrace.data.model.response.RastreoAsistenciaResponse
import com.upc.worktrace.data.model.response.WorkerResponse
import com.upc.worktrace.data.repository.HorarioDetalleRepository
import com.upc.worktrace.data.repository.MarcarSalidaRepository
import com.upc.worktrace.data.repository.RastreoAsistenciaRepository
import com.upc.worktrace.data.repository.WorkerRepository
import kotlinx.coroutines.launch

class WorkerViewModel : ViewModel() {
    private val repository = WorkerRepository("General")

    private val repositoryHorarioDetalle = HorarioDetalleRepository("General")

    private val repositorMarcarSalida = MarcarSalidaRepository("General")

    private val repositorRastreo = RastreoAsistenciaRepository("General")

    private val _resultadoTrabajador = MutableLiveData<WorkerResponse>()
    val resultadoTrabajador: LiveData<WorkerResponse> = _resultadoTrabajador

    private val _trabajadores = MutableLiveData<List<Trabajador>>()
    val trabajadores: LiveData<List<Trabajador>> = _trabajadores

    private val _horarioDetalle = MutableLiveData<HorarioDetalleResponse>()
    val resultadoHorarioDetalle: LiveData<HorarioDetalleResponse> = _horarioDetalle

    private val _marcarSalida = MutableLiveData<MarcarSalidaResponse>()
    val resultadoMarcarSalida: LiveData<MarcarSalidaResponse> = _marcarSalida

    private val _resueltadoRastreoUbicacion = MutableLiveData<RastreoAsistenciaResponse>()
    val rastreoUbicacion: LiveData<RastreoAsistenciaResponse> = _resueltadoRastreoUbicacion

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    fun registrarTrabajador(
        nombres: String,
        puesto: String,
        jefeInmediato: String,
        idTipoContrato: Int,
        direccion: String,
        telefono: String,
        idDistritoTrabajo: Int,
        password: String
    ) {
        _cargando.value = true
        viewModelScope.launch {
            try {
                val request = WorkerRequest(
                    nombres = nombres,
                    puesto = puesto,
                    jefeInmediato = jefeInmediato,
                    idTipoContrato = idTipoContrato,
                    direccion = direccion,
                    telefono = telefono,
                    idDistritoTrabajo = idDistritoTrabajo,
                    password = password
                )
                val resultado = repository.registrarTrabajador(request)
                _resultadoTrabajador.postValue(resultado)
            } catch (e: Exception) {
                _resultadoTrabajador.postValue(WorkerResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }

    fun obtenerTodosTrabajadores() {
        _cargando.value = true
        viewModelScope.launch {
            try {
                val resultado = repository.obtenerTodosTrabajadores()
                _resultadoTrabajador.postValue(resultado)
                resultado.workers?.let { _trabajadores.postValue(it) }
            } catch (e: Exception) {
                _resultadoTrabajador.postValue(WorkerResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }

    fun obtenerTrabajadorPorId(trabajadorId: Int) {
        _cargando.value = true
        viewModelScope.launch {
            try {
                val resultado = repository.obtenerTrabajadorPorId(trabajadorId)
                _resultadoTrabajador.postValue(resultado)
            } catch (e: Exception) {
                _resultadoTrabajador.postValue(WorkerResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }

    fun actualizarTrabajador(
        trabajadorId: Int,
        nombres: String,
        puesto: String,
        jefeInmediato: String,
        idTipoContrato: Int,
        direccion: String,
        telefono: String,
        idDistritoTrabajo: Int
    ) {
        _cargando.value = true
        viewModelScope.launch {
            try {
                val request = WorkerRequest(
                    nombres = nombres,
                    puesto = puesto,
                    jefeInmediato = jefeInmediato,
                    idTipoContrato = idTipoContrato,
                    direccion = direccion,
                    telefono = telefono,
                    idDistritoTrabajo = idDistritoTrabajo
                )
                val resultado = repository.actualizarTrabajador(trabajadorId, request)
                _resultadoTrabajador.postValue(resultado)
            } catch (e: Exception) {
                _resultadoTrabajador.postValue(WorkerResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }

    fun eliminarTrabajador(trabajadorId: Int) {
        _cargando.value = true
        viewModelScope.launch {
            try {
                val resultado = repository.eliminarTrabajador(trabajadorId)
                _resultadoTrabajador.postValue(resultado)
            } catch (e: Exception) {
                _resultadoTrabajador.postValue(WorkerResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }

    fun registrarHorarioDetalle(
        idhorarioAsignacion: String,
        idDiaSemana: String,
        horarioEntrada: String,
        horarioSalida: String
    ) {
        _cargando.value = true
        viewModelScope.launch {
            try {

                val request = HorarioDetalleRequest(
                    idHorarioAsignacion = idhorarioAsignacion,
                    diaSemana = idDiaSemana,
                    horaEntrada = horarioEntrada,
                    horaSalida = horarioSalida
                )
                val resultado = repositoryHorarioDetalle.RegistrarHorarioDetalle(request)
                _horarioDetalle.postValue(resultado)
            } catch (e: Exception) {
                _horarioDetalle.postValue(HorarioDetalleResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }

    fun registrarSalida(
        idAsistencia: String,
        tipoMarcacion: String,
        fechaMarcacion: String,
        horaMarcacion: String,
        latitud: String,
        longitud: String,
        ubicacion: String,
        fuente: String
    ) {
        _cargando.value = true
        viewModelScope.launch {
            try {

                val request = MarcarSalidaRequest(
                    idAsistencia = idAsistencia,
                    tipoMarcacion = tipoMarcacion,
                    fechaMarcacion = fechaMarcacion,
                    horaMarcacion = horaMarcacion,
                    latitud = latitud,
                    longitud = longitud,
                    ubicacion = ubicacion,
                    fuente = fuente
                )
                val resultado = repositorMarcarSalida.RegistrarMarcarSalida(request)
                _marcarSalida.postValue(resultado)
            } catch (e: Exception) {
                _marcarSalida.postValue(MarcarSalidaResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }

    fun obtenerRasreoUbicacion(idAsistencia: Int) {
        _cargando.value = true
        viewModelScope.launch {
            try {
                val resultado = repositorRastreo.mostrarRastreoAsistencia(idAsistencia)
                _resueltadoRastreoUbicacion.postValue(resultado)
            } catch (e: Exception) {
                _resueltadoRastreoUbicacion.postValue(RastreoAsistenciaResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }
} 