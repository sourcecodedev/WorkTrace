package com.upc.worktrace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.entities.DistritoTrabajo
import com.upc.worktrace.data.repository.DistritoRepository
import com.upc.worktrace.ui.adapter.SpinnerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DistritoViewModel @Inject constructor() : ViewModel() {
    private val repository = DistritoRepository("yhimy", null)
    
    private val _distritosSpinner = MutableLiveData<List<SpinnerItem<Int>>>()
    val distritosSpinner: LiveData<List<SpinnerItem<Int>>> = _distritosSpinner
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun cargarDistritos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.listarDistritos()
                _distritosSpinner.value = response.distrito.map { distrito ->
                    SpinnerItem(
                        id = distrito.idDistritoTrabajo,
                        nombre = distrito.nombre
                    )
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
                _distritosSpinner.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
} 