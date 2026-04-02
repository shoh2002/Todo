package com.example.todo

import androidx.compose.runtime.mutableStateListOf

object TodoStorage {
    val tasks = mutableStateListOf<TodoModel>()
}