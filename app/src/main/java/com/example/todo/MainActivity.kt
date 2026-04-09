@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.example.todo.data.TodoRepository
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.ui.viewmodel.TodoViewModel
import com.example.todo.ui.viewmodel.TodoViewModelFactory

/**
 * СЛОЙ UI — Activity
 * Наблюдает StateFlow через ViewModel.
 * Создаёт зависимости: Repository → ViewModel.
 */
class MainActivity : ComponentActivity() {

    // Создаём Repository (в реальном проекте — через DI, например Hilt)
    private val repository = TodoRepository()

    // viewModels() создаёт ViewModel и привязывает к жизненному циклу Activity
    private val viewModel: TodoViewModel by viewModels {
        TodoViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
