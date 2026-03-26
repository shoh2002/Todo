package com.example.todo

import androidx.compose.runtime.mutableStateListOf

object TodoStorage {
    val tasks = mutableStateListOf<TodoModel>()

    fun removeTask(task: TodoModel) {
        tasks.remove(task)
    }
}