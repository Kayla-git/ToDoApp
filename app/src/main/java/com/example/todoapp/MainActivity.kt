package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.data.TaskDatabase
import com.example.todoapp.data.TaskRepository
import com.example.todoapp.navigation.AppNavGraph
import com.example.todoapp.ui.theme.TodoAppTheme
import com.example.todoapp.viewmodel.TaskViewModel
import com.example.todoapp.viewmodel.TaskViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Creamos la base de datos, el repositorio y el ViewModel manualmente.
        val database = TaskDatabase.getDatabase(applicationContext)
        val repository = TaskRepository(database.taskDao())
        val factory = TaskViewModelFactory(repository)

        val viewModel = ViewModelProvider(this, factory)[TaskViewModel::class.java]

        setContent {
            TodoAppTheme {
                AppNavGraph(viewModel = viewModel)
            }
        }
    }
}