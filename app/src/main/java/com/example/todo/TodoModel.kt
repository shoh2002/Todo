package com.example.todo

import java.util.UUID

data class TodoModel(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val completed: Boolean = false
)
