package com.example.todo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.todo.ui.components.TodoItem
import com.example.todo.ui.viewmodel.TodoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    viewModel: TodoViewModel,
    modifier: Modifier = Modifier,
    onTaskClick: (String) -> Unit = {}
) {

    val tasks by viewModel.tasks.collectAsState()

    var isAddingANewTask by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredTasks = if (searchQuery.isBlank()) {
        tasks
    } else {
        tasks.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = { Text("TODO") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { isAddingANewTask = true }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Поиск задач...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Поиск")
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Очистить")
                        }
                    }
                },
                singleLine = true
            )

            LazyColumn {
                items(filteredTasks, key = { it.id }) { todoModel ->
                    TodoItem(
                        item = todoModel,
                        onClick = { onTaskClick(todoModel.id) },
                        onDelete = { viewModel.deleteTask(todoModel) },
                        onToggle = { viewModel.toggleTask(todoModel) }
                    )
                }
            }
        }
    }

    if (isAddingANewTask) {
        var taskName by remember { mutableStateOf("") }
        val context = LocalContext.current

        AlertDialog(
            onDismissRequest = { isAddingANewTask = false },
            title = { Text("Добавить задачу") },
            text = {
                TextField(
                    value = taskName,
                    onValueChange = { taskName = it },
                    placeholder = { Text("Имя задачи") }
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (taskName.isBlank()) {
                        Toast.makeText(context, "Имя не может быть пустым!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Событие → ViewModel
                        viewModel.addTask(taskName)
                        isAddingANewTask = false
                    }
                }) { Text("Добавить") }
            },
            dismissButton = {
                TextButton(onClick = { isAddingANewTask = false }) { Text("Отмена") }
            }
        )
    }
}
