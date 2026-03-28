package com.example.todo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.TodoItem
import com.example.todo.TodoModel
import com.example.todo.TodoStorage
import com.example.todo.ui.theme.TodoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    onTaskClick: (String) -> Unit = {}  // ← 1. ДОБАВИЛИ ЭТОТ ПАРАМЕТР
) {
    var isAddingANewTask by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text("TODO")
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isAddingANewTask = true
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Кнопка добавить"
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TodoStorage.tasks.forEach { todoModel ->
                TodoItem(
                    item = todoModel,
                    onClick = { onTaskClick(todoModel.id) }  // ← 2. ДОБАВИЛИ onClick СЮДА
                )
            }
        }
    }

    if (isAddingANewTask) {
        var taskName by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        AlertDialog(
            icon = null,
            onDismissRequest = {
                isAddingANewTask = false
            },
            title = {
                Text("Добавить задачу")
            },
            text = {
                TextField(
                    value = taskName,
                    onValueChange = { taskName = it },
                    placeholder = {
                        Text("Имя задачи")
                    }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (taskName.isBlank()) {
                            Toast.makeText(
                                context,
                                "Имя задачи не может быть пустым!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            TodoStorage.tasks.add(
                                TodoModel(
                                    name = taskName,
                                    completed = false
                                )
                            )
                            isAddingANewTask = false
                        }
                    }
                ) {
                    Text(text = "Добавить")
                }
            },
            dismissButton = {
                TextButton(onClick = { isAddingANewTask = false }) {
                    Text(text = "Отмена")
                }
            }
        )
    }
}

@Preview
@Composable
private fun TodoScreenPreview() = TodoTheme {
    TodoScreen()
}