package com.upc.worktrace.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.response.WorkerResponse
import com.upc.worktrace.data.repository.WorkerRepository
import kotlinx.coroutines.launch

class TrabajadorViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = WorkerRepository("WorkTrace")
    
    private val _trabajador = MutableLiveData<WorkerResponse>()
    val trabajador: LiveData<WorkerResponse> = _trabajador
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun cargarTrabajador(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.obtenerTrabajadorPorId(id)
                if (response.success && response.data?.isNotEmpty() == true) {
                    _trabajador.value = response
                    _error.value = null
                } else {
                    _error.value = response.message ?: "No se encontró el trabajador"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }
} 