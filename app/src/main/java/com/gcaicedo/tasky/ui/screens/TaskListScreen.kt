package com.gcaicedo.tasky.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gcaicedo.tasky.data.model.Task
import com.gcaicedo.tasky.ui.MainSection
import com.gcaicedo.tasky.ui.components.TaskCard
import com.gcaicedo.tasky.ui.components.TaskyBottomBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onTaskClick: (String) -> Unit,
    onToggleCompleted: (String) -> Unit,
    onAddTask: () -> Unit,
    onSectionChange: (MainSection) -> Unit
) {
    val filters = listOf("Todas", "Pendientes", "Completadas")
    var selectedFilter by rememberSaveable { mutableIntStateOf(0) }
    var searchVisible by rememberSaveable { mutableStateOf(false) }
    var query by rememberSaveable { mutableStateOf("") }

    val filteredTasks = remember(tasks, selectedFilter, query) {
        tasks.filter { task ->
            val matchesFilter = when (selectedFilter) {
                1 -> !task.completed
                2 -> task.completed
                else -> true
            }
            val matchesQuery = query.isBlank() ||
                task.title.contains(query, ignoreCase = true) ||
                task.description.contains(query, ignoreCase = true)
            matchesFilter && matchesQuery
        }
    }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text("Mis Tareas", fontWeight = FontWeight.Bold) },
                    actions = {
                        IconButton(onClick = { searchVisible = !searchVisible }) {
                            Icon(
                                imageVector = if (searchVisible) Icons.Outlined.Close else Icons.Outlined.Search,
                                contentDescription = if (searchVisible) "Cerrar búsqueda" else "Buscar"
                            )
                        }
                    }
                )
                if (searchVisible) {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Buscar tareas...") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
                ScrollableTabRow(selectedTabIndex = selectedFilter, edgePadding = 16.dp) {
                    filters.forEachIndexed { index, label ->
                        Tab(
                            selected = selectedFilter == index,
                            onClick = { selectedFilter = index },
                            text = { Text(label) }
                        )
                    }
                }
            }
        },
        bottomBar = {
            TaskyBottomBar(
                selected = MainSection.TASKS,
                onSelected = onSectionChange
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Filled.Add, contentDescription = "Crear nueva tarea")
            }
        }
    ) { innerPadding ->
        if (filteredTasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (tasks.isEmpty()) "Aún no tienes tareas" else "No hay tareas para mostrar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = if (tasks.isEmpty()) "Pulsa + para registrar tu primera tarea." else "Cambia el filtro o la búsqueda.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredTasks, key = { it.id }) { task ->
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
