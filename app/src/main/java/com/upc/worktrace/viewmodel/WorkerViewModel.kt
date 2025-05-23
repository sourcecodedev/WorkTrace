package com.upc.worktrace.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.upc.worktrace.data.model.entities.Trabajador
import com.upc.worktrace.data.model.request.WorkerRequest
import com.upc.worktrace.data.model.response.WorkerResponse
import com.upc.worktrace.data.repository.WorkerRepository
import kotlinx.coroutines.launch

class WorkerViewModel : ViewModel() {
    private val repository = WorkerRepository("yhimy", null)
    private val gson = GsonBuilder().setPrettyPrinting().create()

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
                
                val response = repository.registrarTrabajador(request)
                
                if (!response.isSuccessful) {
                    throw Exception("Error al registrar trabajador: ${response.code()} - ${response.message()}")
                }
                
                val bodyResponse = response.body()
                if (bodyResponse == null) {
                    throw Exception("Respuesta vacía del servidor")
                }
                
                val successResponse = WorkerResponse(
                    success = true,
                    message = bodyResponse.message ?: "Trabajador registrado correctamente",
                    statusCode = response.code(),
                    data = null,
                    workers = null,
                    bodyString = bodyResponse.bodyString
                )
                
                _resultadoTrabajador.postValue(successResponse)
            } catch (e: Exception) {
                val errorResponse = WorkerResponse(
                    success = false,
                    message = e.message ?: "Error desconocido al registrar trabajador",
                    statusCode = 500,
                    data = null,
                    workers = null,
                    bodyString = null
                )
                _resultadoTrabajador.postValue(errorResponse)
            } finally {
                _cargando.value = false
            }
        }
    }
} 