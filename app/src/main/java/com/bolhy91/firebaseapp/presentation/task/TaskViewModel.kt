package com.bolhy91.firebaseapp.presentation.task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bolhy91.firebaseapp.domain.model.Task
import com.bolhy91.firebaseapp.domain.repository.FirestoreRepository
import com.bolhy91.firebaseapp.utils.Resource
import com.bolhy91.firebaseapp.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _taskState = mutableStateOf<Response<List<Task>>>(Response.Loading)
    val tasksState: State<Response<List<Task>>> = _taskState

    private val _isTaskAddedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isTaskAddedState: State<Response<Void?>> = _isTaskAddedState

    private val _isTaskDeletedState = mutableStateOf<Response<Void?>>(Response.Success(null))
    val isTaskDeletedState: State<Response<Void?>> = _isTaskDeletedState

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            firestoreRepository.addTask(title, description)
                .collect { result ->
                    _isTaskAddedState.value = result
                }
        }
    }

    fun getTasks() = viewModelScope.launch {
        firestoreRepository.getTask().collect { tasks ->
            _taskState.value = tasks
        }
    }

    fun deleteTask(taskId: String) = viewModelScope.launch {
        firestoreRepository.removeTask(taskId).collect { result ->
            _isTaskDeletedState.value = result
        }
    }
}