package com.example.todo


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo.ui.screens.TodoScreen

object Routes {
    const val TODO_LIST = "todo_list"
    const val TODO_DETAIL = "todo_detail/{taskId}"

    fun todoDetail(taskId: String) = "todo_detail/$taskId"
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.TODO_LIST
    ) {
        composable(Routes.TODO_LIST) {
            TodoScreen(
                onTaskClick = { taskId ->

                    navController.navigate(Routes.todoDetail(taskId))
                }
            )
        }

        composable(Routes.TODO_DETAIL) { backStackEntry ->

            val taskId = backStackEntry.arguments?.getString("taskId") ?: return@composable

            val task = TodoStorage.tasks.find { it.id == taskId }

            if (task != null) {
                DetailScreen(
                    task = task,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}