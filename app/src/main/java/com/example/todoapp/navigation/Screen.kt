package com.example.todoapp.navigation

// Rutas de navegación de la app.
sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object AddEditTask : Screen("task/{taskId}") {
        fun createRoute(taskId: Int): String = "task/$taskId"
    }
}
