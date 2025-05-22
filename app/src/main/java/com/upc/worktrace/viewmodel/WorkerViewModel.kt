package com.upc.worktrace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.entities.Trabajador
import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.WorkerResponse
import com.upc.worktrace.data.repository.WorkerRepository
import kotlinx.coroutines.launch

class WorkerViewModel : ViewModel() {
    private val repository = WorkerRepository("General")

    private val _resultadoTrabajador = MutableLiveData<WorkerResponse>()
    val resultadoTrabajador: LiveData<WorkerResponse> = _resultadoTrabajador

    private val _trabajadores = MutableLiveData<List<Trabajador>>()
    val trabajadores: LiveData<List<Trabajador>> = _trabajadores

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
} 