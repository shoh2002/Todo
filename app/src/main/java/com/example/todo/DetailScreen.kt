package com.example.todo.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.ui.viewmodel.TodoViewModel

/**
 * СЛОЙ UI — Jetpack Compose экран деталей задачи
 * Состояние получает из ViewModel через collectAsState()
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    taskId: String,
    viewModel: TodoViewModel,
    onBack: () -> Unit
) {
    // Берём актуальное состояние из ViewModel
    val tasks by viewModel.tasks.collectAsState()
    val task = tasks.find { it.id == taskId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(task?.name ?: "Задача") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (task != null) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Название задачи",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = task.name,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = "Статус",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = if (task.completed) "✅ Выполнено" else "⏳ В процессе",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                Text("Задача не найдена")
            }
        }
    }
}
