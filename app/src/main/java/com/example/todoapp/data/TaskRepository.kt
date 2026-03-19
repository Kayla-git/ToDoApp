package com.example.todoapp.data

// El repositorio actúa como capa intermedia entre DAO y ViewModel.
class TaskRepository(private val taskDao: TaskDao) {

    val allTasks = taskDao.getAllTasks()

    suspend fun getTaskById(id: Int): TaskEntity? {
        return taskDao.getTaskById(id)
    }

    suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }
}