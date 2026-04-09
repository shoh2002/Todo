package com.example.todo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.ui.screens.DetailScreen
import com.example.todo.ui.screens.TodoScreen
import com.example.todo.ui.viewmodel.TodoViewModel

object Routes {
    const val TODO_LIST = "todo_list"
    const val TODO_DETAIL = "todo_detail/{taskId}"

    fun todoDetail(taskId: String) = "todo_detail/$taskId"
}

/**
 * СЛОЙ UI — Навигация
 * ViewModel передаётся в каждый экран как зависимость.
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: TodoViewModel           // ViewModel живёт в MainActivity
) {
    NavHost(
        navController = navController,
        startDestination = Routes.TODO_LIST
    ) {
        composable(Routes.TODO_LIST) {
            TodoScreen(
                viewModel = viewModel,
                onTaskClick = { taskId ->
                    navController.navigate(Routes.todoDetail(taskId))
                }
            )
        }

        composable(Routes.TODO_DETAIL) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")
                ?: return@composable

            DetailScreen(
                taskId = taskId,
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
