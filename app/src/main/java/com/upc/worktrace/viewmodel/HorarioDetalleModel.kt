package com.upc.worktrace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.request.HorarioDetalleRequest
import com.upc.worktrace.data.model.response.HorarioDetalleResponse
import com.upc.worktrace.data.repository.HorarioAsignacionRepository
import com.upc.worktrace.data.repository.HorarioDetalleRepository
import kotlinx.coroutines.launch


class HorarioDetalleModel: ViewModel(){

    private val repository = HorarioDetalleRepository("Ever", null)
    private val _horarioDetalle = MutableLiveData<HorarioDetalleResponse>()
    val resultadoHorarioDetalle: LiveData<HorarioDetalleResponse> = _horarioDetalle

    private val _cargando = MutableLiveData<Boolean>()
    val cargando: LiveData<Boolean> = _cargando

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
                val resultado = repository.RegistrarHorarioDetalle(request)
                _horarioDetalle.postValue(resultado)
            } catch (e: Exception) {
                _horarioDetalle.postValue(HorarioDetalleResponse(false, "Error: ${e.message}", null))
            } finally {
                _cargando.value = false
            }
        }
    }
}