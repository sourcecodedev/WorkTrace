package com.upc.worktrace.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.upc.worktrace.data.model.response.DistritoBodyResponse
import com.upc.worktrace.data.repository.DistritoRepository
import com.upc.worktrace.ui.adapter.SpinnerItem
import kotlinx.coroutines.launch

class DistritoViewModel : ViewModel() {
    private val TAG = "DistritoViewModel"
    private val repository = DistritoRepository("yhimy", null)
    private val gson = GsonBuilder().setPrettyPrinting().create()
    
    private val _distritosSpinner = MutableLiveData<List<SpinnerItem<Int>>>()
    val distritosSpinner: LiveData<List<SpinnerItem<Int>>> = _distritosSpinner
    
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        Log.d(TAG, "Inicializando DistritoViewModel")
        cargarDistritos()
    }
    
    fun cargarDistritos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                Log.d(TAG, "Cargando distritos...")
                val response = repository.listarDistritos()
                
                // Imprimir la respuesta completa del servidor
                Log.d(TAG, """
                    |┌────── Respuesta de Distritos del Servidor ──────
                    |${gson.toJson(response).split("\n").joinToString("\n|")}
                    |└─────────────────────────
                """.trimMargin())
                
                if (response.bodyString.isNullOrEmpty()) {
                    throw Exception("La respuesta del servidor está vacía")
                }
                
                // Parsear el body string a DistritoBodyResponse
                val bodyResponse = try {
                    gson.fromJson(response.bodyString, DistritoBodyResponse::class.java)
                } catch (e: JsonSyntaxException) {
                    Log.e(TAG, "Error al parsear la respuesta: ${response.bodyString}", e)
                    throw Exception("Error al procesar la respuesta del servidor")
                }
                
                if (bodyResponse == null) {
                    throw Exception("No se pudo procesar la respuesta del servidor")
                }
                
                // Validar la respuesta
                if (!bodyResponse.success) {
                    throw Exception("Error del servidor: ${bodyResponse.message}")
                }
                
                if (bodyResponse.distrito.isEmpty()) {
                    Log.w(TAG, "La lista de distritos está vacía")
                    _distritosSpinner.value = emptyList()
                    return@launch
                }
                
                // Imprimir cada distrito recibido
                bodyResponse.distrito.forEach { distrito ->
                    Log.d(TAG, """
                        |Distrito recibido:
                        |├── ID: ${distrito.idDistritoTrabajo}
                        |└── Nombre: ${distrito.nombre}
                    """.trimMargin())
                }
                
                val distritos = bodyResponse.distrito.map { distrito ->
                    SpinnerItem(
                        id = distrito.idDistritoTrabajo,
                        nombre = distrito.nombre
                    ).also { item ->
                        Log.d(TAG, "Mapeado: ID=${item.id}, Nombre=${item.nombre}")
                    }
                }
                
                Log.d(TAG, """
                    |┌────── Distritos mapeados para Spinner ──────
                    |${distritos.joinToString("\n|") { "ID: ${it.id}, Nombre: ${it.nombre}" }}
                    |└─────────────────────────
                """.trimMargin())
                
                _distritosSpinner.value = distritos
            } catch (e: Exception) {
                Log.e(TAG, "Error al cargar distritos", e)
                _error.value = e.message ?: "Error desconocido"
                _distritosSpinner.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
} 