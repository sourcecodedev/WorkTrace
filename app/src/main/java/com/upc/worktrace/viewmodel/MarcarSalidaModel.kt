package com.upc.worktrace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.request.MarcarSalidaRequest
import com.upc.worktrace.data.model.response.MarcarSalidaResponse
import com.upc.worktrace.data.repository.MarcarSalidaRepository
import kotlinx.coroutines.launch

class MarcarSalidaModel: ViewModel() {
    private val repository = MarcarSalidaRepository("Ever", null)

    private val _marcarSalida = MutableLiveData<MarcarSalidaResponse>()
    val resultadoMarcarSalida: LiveData<MarcarSalidaResponse> = _marcarSalida

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

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
                val resultado = repository.RegistrarMarcarSalida(request)
                _marcarSalida.postValue(resultado)
            } catch (e: Exception) {
                _marcarSalida.postValue(MarcarSalidaResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }
}