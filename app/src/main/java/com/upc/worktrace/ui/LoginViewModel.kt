package com.upc.worktrace.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.LoginResponse
import com.upc.worktrace.data.repository.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val repository = LoginRepository()

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val result = repository.login(username, password)
                _loginResult.postValue(result)
            } catch (e: Exception) {
                _loginResult.postValue(LoginResponse(
                    false, "Error: ${e.message}", null
                ))
            }

        }
    }

}