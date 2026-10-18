package com.gcaicedo.tasky.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.gcaicedo.tasky.data.model.Priority
import com.gcaicedo.tasky.data.model.Task
import com.gcaicedo.tasky.ui.theme.TaskyGreen
import com.gcaicedo.tasky.ui.theme.TaskyOrange
import com.gcaicedo.tasky.ui.theme.TaskyRed
import com.gcaicedo.tasky.util.formatDate

@Composable
fun TaskCard(
    task: Task,
    onClick: () -> Unit,
    onToggleCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (task.completed) Icons.Filled.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                contentDescription = if (task.completed) "Tarea completada" else "Marcar como completada",
                tint = if (task.completed) TaskyGreen else priorityColor(task.priority),
                modifier = Modifier
                    .size(28.dp)
                    .clickable(onClick = onToggleCompleted)
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    textDecoration = if (task.completed) TextDecoration.LineThrough else null
                )
                Text(
                    text = formatDate(task.dueDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = task.priority.displayName,
                    style = MaterialTheme.typography.labelMedium,
                    color = priorityColor(task.priority),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

fun priorityColor(priority: Priority): Color = when (priority) {
    Priority.LOW -> TaskyGreen
    Priority.MEDIUM -> TaskyOrange
    Priority.HIGH -> TaskyRed
}
