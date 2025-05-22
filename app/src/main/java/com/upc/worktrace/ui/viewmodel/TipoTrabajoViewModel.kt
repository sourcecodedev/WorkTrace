package com.upc.worktrace.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.entities.TipoContrato
import com.upc.worktrace.data.repository.TipoTrabajoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipoTrabajoViewModel @Inject constructor(
    private val repository: TipoTrabajoRepository
) : ViewModel() {
    
    private val _tiposTrabajo = MutableLiveData<List<TipoContrato>?>()
    val tiposTrabajo: LiveData<List<TipoContrato>?> = _tiposTrabajo
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun cargarTiposTrabajo() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.listarTiposTrabajo()
                if (response.success) {
                    _tiposTrabajo.value = response.contrato
                } else {
                    _error.value = response.message
                    _tiposTrabajo.value = null
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
                _tiposTrabajo.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }
} 