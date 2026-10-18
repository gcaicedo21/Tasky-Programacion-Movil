package com.gcaicedo.tasky.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gcaicedo.tasky.ui.MainSection
import com.gcaicedo.tasky.ui.components.TaskyBottomBar
import com.gcaicedo.tasky.ui.theme.TaskyPurple
import com.gcaicedo.tasky.ui.theme.TaskyPurpleSoft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onSectionChange: (MainSection) -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil", fontWeight = FontWeight.Bold) }) },
        bottomBar = {
            TaskyBottomBar(selected = MainSection.PROFILE, onSelected = onSectionChange)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(120.dp).background(TaskyPurpleSoft, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.TaskAlt,
                    contentDescription = null,
                    tint = TaskyPurple,
                    modifier = Modifier.size(72.dp)
                )
            }
            Text(
                text = "TASKY",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = TaskyPurple,
                modifier = Modifier.padding(top = 20.dp)
            )
            Text(
                text = "Organiza tus pendientes y realiza seguimiento de tus actividades de forma sencilla.",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "Proyecto académico · Programación móvil",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 24.dp)
            )
        }
    }
}
