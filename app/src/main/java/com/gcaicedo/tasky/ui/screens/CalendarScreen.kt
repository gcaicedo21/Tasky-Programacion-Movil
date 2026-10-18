package com.gcaicedo.tasky.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gcaicedo.tasky.data.model.Task
import com.gcaicedo.tasky.ui.MainSection
import com.gcaicedo.tasky.ui.components.TaskCard
import com.gcaicedo.tasky.ui.components.TaskyBottomBar
import com.gcaicedo.tasky.util.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    tasks: List<Task>,
    onTaskClick: (String) -> Unit,
    onToggleCompleted: (String) -> Unit,
    onSectionChange: (MainSection) -> Unit
) {
    val grouped = tasks.sortedBy { it.dueDate }.groupBy { it.dueDate }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Calendario", fontWeight = FontWeight.Bold) }) },
        bottomBar = {
            TaskyBottomBar(selected = MainSection.CALENDAR, onSelected = onSectionChange)
        }
    ) { innerPadding ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay tareas programadas", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                grouped.forEach { (date, dateTasks) ->
                    item(key = "header_$date") {
                        Text(
                            text = formatDate(date),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
                        )
                    }
                    items(dateTasks, key = { it.id }) { task ->
                        TaskCard(
                            task = task,
                            onClick = { onTaskClick(task.id) },
                            onToggleCompleted = { onToggleCompleted(task.id) }
                        )
                    }
                }
            }
        }
    }
}
