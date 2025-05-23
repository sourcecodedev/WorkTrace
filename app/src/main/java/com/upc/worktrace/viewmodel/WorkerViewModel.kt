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
import com.upc.worktrace.data.model.response.WorkerBodyResponse
import com.upc.worktrace.data.repository.WorkerRepository
import com.upc.worktrace.ui.viewmodel.TrabajadorViewModel
import kotlinx.coroutines.launch
import java.util.Date

class WorkerViewModel : ViewModel() {
    private val TAG = "WorkerViewModel"
    private val repository = WorkerRepository("yhimy", null)
    private val gson = GsonBuilder().setPrettyPrinting().create()

    private val _resultadoTrabajador = MutableLiveData<WorkerResponse>()
    val resultadoTrabajador: LiveData<WorkerResponse> = _resultadoTrabajador

    private val _trabajadores = MutableLiveData<List<Trabajador>>()
    val trabajadores: LiveData<List<Trabajador>> = _trabajadores

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    private var listaTrabajadoresCompleta = listOf<Trabajador>()

    fun obtenerTrabajadores() {
        Log.d(TAG, "Iniciando obtención de lista completa de trabajadores")
        _cargando.value = true
        viewModelScope.launch {
            try {
                val response = repository.obtenerTodosTrabajadores()
                Log.d(TAG, "Respuesta del servidor: ${gson.toJson(response)}")
                
                if (response.success && response.data != null) {
                    listaTrabajadoresCompleta = response.data
                    Log.d(TAG, "Lista de trabajadores obtenida. Cantidad: ${listaTrabajadoresCompleta.size}")
                    if (listaTrabajadoresCompleta.isNotEmpty()) {
                        Log.d(TAG, "Primer trabajador de la lista: ${gson.toJson(listaTrabajadoresCompleta.first())}")
                    }
                    _trabajadores.value = listaTrabajadoresCompleta
                } else {
                    Log.e(TAG, "Error al obtener trabajadores: ${response.message}")
                    _trabajadores.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al obtener trabajadores", e)
                Log.e(TAG, "Stacktrace: ${e.stackTraceToString()}")
                _trabajadores.value = emptyList()
            } finally {
                _cargando.value = false
                Log.d(TAG, "Finalizada la obtención de trabajadores")
            }
        }
    }

    fun obtenerTrabajadorPorId(idTrabajador: Int) {
        Log.d(TAG, "Iniciando obtención de trabajador específico - ID: $idTrabajador")
        _cargando.value = true
        viewModelScope.launch {
            try {
                val response = repository.obtenerTrabajadorPorId(idTrabajador)
                Log.d(TAG, "Respuesta del servidor: ${gson.toJson(response)}")
                
                if (response.success && response.data?.isNotEmpty() == true) {
                    val trabajador = response.data.first()
                    Log.d(TAG, "Trabajador específico obtenido: ${trabajador.nombres}")
                    Log.d(TAG, "Detalles del trabajador: ${gson.toJson(trabajador)}")
                    listaTrabajadoresCompleta = response.data
                    _trabajadores.value = listaTrabajadoresCompleta
                } else {
                    Log.w(TAG, "No se encontró el trabajador con ID: $idTrabajador")
                    _trabajadores.value = emptyList()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al obtener trabajador", e)
                Log.e(TAG, "Stacktrace: ${e.stackTraceToString()}")
                _trabajadores.value = emptyList()
            } finally {
                _cargando.value = false
                Log.d(TAG, "Finalizada la obtención del trabajador")
            }
        }
    }

    fun filtrarTrabajadores(query: String) {
        Log.d(TAG, "Iniciando filtrado de trabajadores con query: '$query'")
        if (query.isEmpty()) {
            Log.d(TAG, "Query vacío, mostrando lista completa de ${listaTrabajadoresCompleta.size} trabajadores")
            _trabajadores.value = listaTrabajadoresCompleta
            return
        }

        val queryLowerCase = query.lowercase()
        val listaFiltrada = listaTrabajadoresCompleta.filter { trabajador ->
            trabajador.nombres.lowercase().contains(queryLowerCase) ||
            trabajador.puesto.lowercase().contains(queryLowerCase) ||
            trabajador.jefeInmediato.lowercase().contains(queryLowerCase) ||
            trabajador.direccion.lowercase().contains(queryLowerCase) ||
            trabajador.telefono.contains(queryLowerCase)
        }
        Log.d(TAG, "Resultados del filtrado: ${listaFiltrada.size} trabajadores encontrados")
        if (listaFiltrada.isNotEmpty()) {
            //Log.d(TAG, "Nombres de trabajadores filtrados: ${listaFiltrada.map { it.nombres }}")
        }
        _trabajadores.value = listaFiltrada
    }

    fun eliminarTrabajador(idTrabajador: Int) {
        Log.d(TAG, "Iniciando proceso de eliminación de trabajador ID: $idTrabajador")
        _cargando.value = true
        viewModelScope.launch {
            try {
                val response = repository.eliminarTrabajador(idTrabajador)
                Log.d(TAG, "Respuesta de eliminación: ${gson.toJson(response)}")
                
                // Parsear el body string a objeto
                val bodyResponse = response.bodyString?.let { bodyStr ->
                    try {
                        gson.fromJson(bodyStr, WorkerBodyResponse::class.java)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error al parsear body response", e)
                        null
                    }
                }
                
                if (bodyResponse?.success == true || response.success) {
                    Log.d(TAG, "Trabajador eliminado correctamente")
                    _resultadoTrabajador.postValue(response.copy(success = true))
                    // Actualizar la lista de trabajadores después de eliminar
                    obtenerTrabajadores()
                } else {
                    Log.e(TAG, "Error al eliminar trabajador: ${response.message}")
                    Log.e(TAG, "Detalles del error: ${response.bodyString}")
                    _resultadoTrabajador.postValue(response)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al eliminar trabajador", e)
                Log.e(TAG, "Stacktrace: ${e.stackTraceToString()}")
                val errorResponse = WorkerResponse(
                    success = false,
                    message = e.message ?: "Error desconocido al eliminar trabajador",
                    statusCode = 500,
                    data = null,
                    bodyString = e.stackTraceToString()
                )
                _resultadoTrabajador.postValue(errorResponse)
            } finally {
                _cargando.value = false
                Log.d(TAG, "Finalizado el proceso de eliminación")
            }
        }
    }

    fun registrarTrabajador(
        nombres: String,
        puesto: String,
        jefeInmediato: String,
        idTipoContrato: Int,
        direccion: String,
        telefono: String,
        idDistritoTrabajo: Int,
        estado: Int = 1
    ) {
        Log.d(TAG, "Iniciando registro de nuevo trabajador:")
        Log.d(TAG, """
            Datos del trabajador:
            - Nombres: $nombres
            - Puesto: $puesto
            - Jefe Inmediato: $jefeInmediato
            - ID Tipo Contrato: $idTipoContrato
            - Dirección: $direccion
            - Teléfono: $telefono
            - ID Distrito: $idDistritoTrabajo
            - Estado: $estado
        """.trimIndent())
        
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
                    estado = estado
                )
                
                Log.d(TAG, "Request de registro: ${gson.toJson(request)}")
                val response = repository.registrarTrabajador(request)
                Log.d(TAG, "Respuesta de registro: ${gson.toJson(response)}")
                
                // Parsear el body para obtener el verdadero estado de éxito
                val bodyResponse = try {
                    gson.fromJson(response.bodyString, WorkerBodyResponse::class.java)
                } catch (e: Exception) {
                    null
                }
                
                if (bodyResponse?.success == true) {
                    Log.d(TAG, "Registro exitoso según el body de la respuesta")
                    _resultadoTrabajador.postValue(response.copy(success = true))
                    obtenerTrabajadores()
                } else {
                    Log.e(TAG, "Error en el registro: ${response.message}")
                    Log.e(TAG, "Detalles del error: ${response.bodyString}")
                    _resultadoTrabajador.postValue(response)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error al registrar trabajador", e)
                Log.e(TAG, "Stacktrace: ${e.stackTraceToString()}")
                val errorResponse = WorkerResponse(
                    success = false,
                    message = e.message ?: "Error desconocido al registrar trabajador",
                    statusCode = 500,
                    data = null,
                    bodyString = e.stackTraceToString()
                )
                _resultadoTrabajador.postValue(errorResponse)
            } finally {
                _cargando.value = false
                Log.d(TAG, "Finalizado el proceso de registro")
            }
        }
    }

    private fun obtenerTrabajador(
        idTrabajador: Int,
        idUsuario: Int,
        estado: String,
        fechaRegistro: Date,
        idUsuarioCreador: Int
    ) {
    }

    private fun actualizarTrabajador(trabajador: Trabajador) {}

} 