package com.upc.worktrace.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upc.worktrace.data.model.response.LoginResponse
import com.upc.worktrace.data.repository.WorkerRepository
import kotlinx.coroutines.launch

class WorkerViewModel : ViewModel() {

    private val repository = WorkerRepository("Yhimy")


}