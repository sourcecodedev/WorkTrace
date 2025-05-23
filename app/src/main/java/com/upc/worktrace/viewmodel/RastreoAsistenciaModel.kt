package com.upc.worktrace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.response.RastreoAsistenciaResponse
import com.upc.worktrace.data.repository.RastreoAsistenciaRepository
import kotlinx.coroutines.launch

class RastreoAsistenciaModel : ViewModel(){
    private val repository = RastreoAsistenciaRepository("Ever")

    private val _resueltadoRastreoUbicacion = MutableLiveData<RastreoAsistenciaResponse>()
    val rastreoUbicacion: LiveData<RastreoAsistenciaResponse> = _resueltadoRastreoUbicacion

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

    fun obtenerRasreoUbicacion(idAsistencia: Int) {
        _cargando.value = true
        viewModelScope.launch {
            try {
                val resultado = repository.mostrarRastreoAsistencia(idAsistencia)
                _resueltadoRastreoUbicacion.postValue(resultado)
            } catch (e: Exception) {
                _resueltadoRastreoUbicacion.postValue(RastreoAsistenciaResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }
}