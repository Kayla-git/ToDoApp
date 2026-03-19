package com.example.todoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.data.TaskEntity
import com.example.todoapp.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    taskId: Int,
    viewModel: TaskViewModel,
    onBack: () -> Unit
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var currentTask by remember { mutableStateOf<TaskEntity?>(null) }

    // Si taskId es distinto de -1, cargamos la tarea para editarla.
    LaunchedEffect(taskId) {
        if (taskId != -1) {
            currentTask = viewModel.findTaskById(taskId)
            currentTask?.let { task ->
                title = task.title
                description = task.description
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (taskId == -1) "Nueva tarea" else "Editar tarea")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (title.isBlank()) return@Button

                    if (taskId == -1) {
                        viewModel.addTask(
                            title = title.trim(),
                            description = description.trim()
                        )
                    } else {
                        viewModel.updateTask(
                            id = taskId,
                            title = title.trim(),
                            description = description.trim(),
                            isDone = currentTask?.isDone ?: false
                        )
                    }

                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (taskId == -1) "Guardar tarea" else "Actualizar tarea")
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancelar")
            }
        }
    }
}