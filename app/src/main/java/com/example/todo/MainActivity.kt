// com/example/todo/MainActivity.kt
package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.todo.data.TodoDataStore
import com.example.todo.data.TodoRepository
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.viewmodel.TodoViewModel
import com.example.todo.ui.viewmodel.TodoViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataStore = TodoDataStore(applicationContext)
        val dataSource = TasksDataSource(dataStore)
        val repository = TodoRepository(dataSource)

        val viewModel: TodoViewModel by viewModels {
            TodoViewModelFactory(repository)
        }

        setContent {
            TodoTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}
