package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.TaskEntity
import com.example.todoapp.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    // Exponemos las tareas como StateFlow para que Compose reaccione a los cambios.
    val tasks: StateFlow<List<TaskEntity>> = repository.allTasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addTask(title: String, description: String) {
        viewModelScope.launch {
            repository.insertTask(
                TaskEntity(
                    title = title,
                    description = description
                )
            )
        }
    }

    fun updateTask(id: Int, title: String, description: String, isDone: Boolean) {
        viewModelScope.launch {
            repository.updateTask(
                TaskEntity(
                    id = id,
                    title = title,
                    description = description,
                    isDone = isDone
                )
            )
        }
    }

    fun deleteTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun toggleTaskDone(task: TaskEntity) {
        viewModelScope.launch {
            repository.updateTask(task.copy(isDone = !task.isDone))
        }
    }

    suspend fun findTaskById(id: Int): TaskEntity? {
        return repository.getTaskById(id)
    }
}