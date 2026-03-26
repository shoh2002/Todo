
package com.example.todo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.ui.theme.TodoTheme
import java.util.UUID

data class TodoModel(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val completed: Boolean
)

@Composable
fun TodoItem(item: TodoModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            TodoStorage.removeTask(item)
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Удалить"
            )
        }

        Text(item.name)

        Checkbox(
            checked = item.completed,
            onCheckedChange = {

            }
        )
    }
}

@Preview
@Composable
private fun TodoItemPreview() = TodoTheme() {
    TodoItem(
        item = TodoModel(
            name = "Task 1",
            completed = true
        )
    )
}