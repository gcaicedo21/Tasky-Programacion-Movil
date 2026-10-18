package com.gcaicedo.tasky.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Checklist
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.gcaicedo.tasky.ui.MainSection

@Composable
fun TaskyBottomBar(
    selected: MainSection,
    onSelected: (MainSection) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selected == MainSection.TASKS,
            onClick = { onSelected(MainSection.TASKS) },
            icon = { Icon(Icons.Outlined.Checklist, contentDescription = "Tareas") },
            label = { Text("Tareas") }
        )
        NavigationBarItem(
            selected = selected == MainSection.CALENDAR,
            onClick = { onSelected(MainSection.CALENDAR) },
            icon = { Icon(Icons.Outlined.CalendarMonth, contentDescription = "Calendario") },
            label = { Text("Calendario") }
        )
        NavigationBarItem(
            selected = selected == MainSection.STATISTICS,
            onClick = { onSelected(MainSection.STATISTICS) },
            icon = { Icon(Icons.Outlined.BarChart, contentDescription = "Estadísticas") },
            label = { Text("Estadísticas") }
        )
        NavigationBarItem(
            selected = selected == MainSection.PROFILE,
            onClick = { onSelected(MainSection.PROFILE) },
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}
