package com.upc.worktrace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.response.DistritoResponse
import com.upc.worktrace.data.repository.DistritoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DistritoViewModel @Inject constructor(
    private val repository: DistritoRepository
) : ViewModel() {
    
    private val _distritos = MutableLiveData<List<DistritoResponse>>()
    val distritos: LiveData<List<DistritoResponse>> = _distritos
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun cargarDistritos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val distritos = repository.listarDistritos()
                _distritos.value = distritos
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }
} 