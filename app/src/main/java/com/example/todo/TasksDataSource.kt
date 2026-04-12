// com/example/todo/TasksDataSource.kt
package com.example.todo

import com.example.todo.data.TodoDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TasksDataSource(private val dataStore: TodoDataStore) {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _tasks = MutableStateFlow<List<TodoModel>>(emptyList())
    val tasks: StateFlow<List<TodoModel>> = _tasks.asStateFlow()

    init {
        scope.launch {
            dataStore.tasksFlow.collect { savedTasks ->
                _tasks.value = savedTasks
            }
        }
    }

    private fun save() {
        scope.launch {
            dataStore.saveTasks(_tasks.value)
        }
    }

    fun addTask(name: String) {
        _tasks.update { current ->
            current + TodoModel(name = name, completed = false)
        }
        save()
    }

    fun deleteTask(task: TodoModel) {
        _tasks.update { current ->
            current.filter { it.id != task.id }
        }
        save()
    }

    fun toggleTask(task: TodoModel) {
        _tasks.update { current ->
            current.map {
                if (it.id == task.id) it.copy(completed = !it.completed) else it
            }
        }
        save()
    }
}
