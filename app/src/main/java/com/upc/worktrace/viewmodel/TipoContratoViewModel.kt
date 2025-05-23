package com.upc.worktrace.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.entities.TipoContrato
import com.upc.worktrace.data.repository.TipoContratoRepository
import com.upc.worktrace.ui.adapter.SpinnerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class TipoContratoViewModel @Inject constructor() : ViewModel() {
    private val repository = TipoContratoRepository("yhimy", null)
    private val TAG = "TipoContratoViewModel"
    
    private val _tiposContratoSpinner = MutableLiveData<List<SpinnerItem<Int>>>()
    val tiposContratoSpinner: LiveData<List<SpinnerItem<Int>>> = _tiposContratoSpinner
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    init {
        Log.d(TAG, "Inicializando TipoContratoViewModel")
        cargarTiposContrato()
    }
    
    fun cargarTiposContrato() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _isLoading.value = true
                
                // Realizar la operación de red en un hilo de IO
                val response = withContext(Dispatchers.IO) {
                    repository.listarTiposContrato()
                }
                
                // Procesar la respuesta en el hilo principal
                val tiposContrato = response.contrato.map { tipoContrato ->
                    SpinnerItem(
                        id = tipoContrato.idTipoContrato,
                        nombre = tipoContrato.nombre
                    )
                }
                
                _tiposContratoSpinner.value = tiposContrato
                Log.d(TAG, "Tipos de contrato cargados: $tiposContrato")
                
            } catch (e: Exception) {
                Log.e(TAG, "Error al cargar tipos de contrato", e)
                _error.value = e.message ?: "Error desconocido"
                _tiposContratoSpinner.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
} 