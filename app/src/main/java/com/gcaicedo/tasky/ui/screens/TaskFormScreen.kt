package com.gcaicedo.tasky.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gcaicedo.tasky.data.model.Priority
import com.gcaicedo.tasky.data.model.Task
import com.gcaicedo.tasky.ui.components.priorityColor
import com.gcaicedo.tasky.util.formatDate
import com.gcaicedo.tasky.util.isoDateToUtcMillis
import com.gcaicedo.tasky.util.todayIso
import com.gcaicedo.tasky.util.utcMillisToIsoDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskFormScreen(
    existingTask: Task? = null,
    onBack: () -> Unit,
    onSave: (title: String, description: String, dueDate: String, priority: Priority) -> Unit
) {
    var title by rememberSaveable(existingTask?.id) { mutableStateOf(existingTask?.title.orEmpty()) }
    var description by rememberSaveable(existingTask?.id) { mutableStateOf(existingTask?.description.orEmpty()) }
    var dueDate by rememberSaveable(existingTask?.id) { mutableStateOf(existingTask?.dueDate ?: todayIso()) }
    var priorityName by rememberSaveable(existingTask?.id) {
        mutableStateOf((existingTask?.priority ?: Priority.MEDIUM).name)
    }
    var titleError by rememberSaveable { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val priority = Priority.valueOf(priorityName)
    val isEditing = existingTask != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Editar tarea" else "Nueva tarea") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Outlined.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (it.isNotBlank()) titleError = false
                },
                label = { Text("Título") },
                placeholder = { Text("Ej. Estudiar programación") },
                singleLine = true,
                isError = titleError,
                supportingText = {
                    if (titleError) Text("El título es obligatorio")
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                placeholder = { Text("Escribe una descripción...") },
                minLines = 4,
                maxLines = 6,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Fecha límite", fontWeight = FontWeight.SemiBold)
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = formatDate(dueDate),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        Icon(Icons.Outlined.CalendarMonth, contentDescription = null)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker = true }
                )
            }

            Text("Prioridad", fontWeight = FontWeight.SemiBold)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Priority.entries.forEach { item ->
                    FilterChip(
                        selected = priority == item,
                        onClick = { priorityName = item.name },
                        label = { Text(item.displayName) },
                        leadingIcon = {
                            Text(
                                text = "●",
                                color = priorityColor(item)
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    if (title.isBlank()) {
                        titleError = true
                    } else {
                        onSave(title, description, dueDate, priority)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(if (isEditing) "Actualizar tarea" else "Guardar tarea")
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = isoDateToUtcMillis(dueDate)
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { selected ->
                            dueDate = utcMillisToIsoDate(selected)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
