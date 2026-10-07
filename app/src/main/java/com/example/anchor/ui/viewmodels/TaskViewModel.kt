package com.example.anchor.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anchor.data.local.entities.TaskEntitiy
import com.example.anchor.data.repository.AnchorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: AnchorRepository) : ViewModel() {

    val taskUiState: StateFlow<TaskUiState> = repository.getAllTasks()
        .map { tasks -> TaskUiState(taskList = tasks) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TaskUiState()
        )

    fun addTask(title: String) {
        if (title.isBlank()) return
        viewModelScope.launch {
            repository.insertTask(TaskEntitiy(title = title.trim()))
        }
    }

    fun toggleTaskCompletion(task: TaskEntitiy) {
        viewModelScope.launch {
            repository.insertTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    fun deleteTask(task: TaskEntitiy) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class TaskUiState(
    val taskList: List<TaskEntitiy> = listOf()
)
