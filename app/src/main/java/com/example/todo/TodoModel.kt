
package com.example.todo

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
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
fun TodoItem(
    item: TodoModel,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // ← и это
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { TodoStorage.tasks.remove(item) }) {
            Icon(Icons.Default.Delete, contentDescription = "Удалить")
        }
        Text(
            text = item.name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Checkbox(
            checked = item.completed,
            onCheckedChange = {}
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