package com.gcaicedo.tasky.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gcaicedo.tasky.ui.screens.CalendarScreen
import com.gcaicedo.tasky.ui.screens.ProfileScreen
import com.gcaicedo.tasky.ui.screens.StatisticsScreen
import com.gcaicedo.tasky.ui.screens.TaskDetailScreen
import com.gcaicedo.tasky.ui.screens.TaskFormScreen
import com.gcaicedo.tasky.ui.screens.TaskListScreen
import com.gcaicedo.tasky.ui.screens.WelcomeScreen

@Composable
fun TaskyApp(viewModel: TaskViewModel = viewModel()) {
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()
    val onboardingCompleted by viewModel.onboardingCompleted.collectAsStateWithLifecycle()

    var route by rememberSaveable { mutableStateOf(ROUTE_TASKS) }
    var previousMainRoute by rememberSaveable { mutableStateOf(ROUTE_TASKS) }
    var selectedTaskId by rememberSaveable { mutableStateOf<String?>(null) }

    if (!onboardingCompleted) {
        WelcomeScreen(
            onContinue = {
                viewModel.completeOnboarding()
                route = ROUTE_TASKS
            }
        )
        return
    }

    fun openTask(taskId: String) {
        if (route in MAIN_ROUTES) previousMainRoute = route
        selectedTaskId = taskId
        route = ROUTE_DETAIL
    }

    fun openSection(section: MainSection) {
        route = when (section) {
            MainSection.TASKS -> ROUTE_TASKS
            MainSection.CALENDAR -> ROUTE_CALENDAR
            MainSection.STATISTICS -> ROUTE_STATISTICS
            MainSection.PROFILE -> ROUTE_PROFILE
        }
    }

    when (route) {
        ROUTE_TASKS -> TaskListScreen(
            tasks = tasks,
            onTaskClick = ::openTask,
            onToggleCompleted = viewModel::toggleCompleted,
            onAddTask = { route = ROUTE_NEW },
            onSectionChange = ::openSection
        )

        ROUTE_CALENDAR -> CalendarScreen(
            tasks = tasks,
            onTaskClick = ::openTask,
            onToggleCompleted = viewModel::toggleCompleted,
            onSectionChange = ::openSection
        )

        ROUTE_STATISTICS -> StatisticsScreen(
            tasks = tasks,
            onSectionChange = ::openSection
        )

        ROUTE_PROFILE -> ProfileScreen(onSectionChange = ::openSection)

        ROUTE_NEW -> TaskFormScreen(
            onBack = { route = ROUTE_TASKS },
            onSave = { title, description, dueDate, priority ->
                viewModel.addTask(title, description, dueDate, priority)
                route = ROUTE_TASKS
            }
        )

        ROUTE_DETAIL -> {
            val task = viewModel.findTask(selectedTaskId)
            if (task == null) {
                LoadingFallback(onReturn = { route = previousMainRoute })
            } else {
                TaskDetailScreen(
                    task = task,
                    onBack = { route = previousMainRoute },
                    onToggleCompleted = { viewModel.toggleCompleted(task.id) },
                    onEdit = { route = ROUTE_EDIT },
                    onDelete = {
                        viewModel.deleteTask(task.id)
                        selectedTaskId = null
                        route = previousMainRoute
                    }
                )
            }
        }

        ROUTE_EDIT -> {
            val task = viewModel.findTask(selectedTaskId)
            if (task == null) {
                LoadingFallback(onReturn = { route = ROUTE_TASKS })
            } else {
                TaskFormScreen(
                    existingTask = task,
                    onBack = { route = ROUTE_DETAIL },
                    onSave = { title, description, dueDate, priority ->
                        viewModel.updateTask(task.id, title, description, dueDate, priority)
                        route = ROUTE_DETAIL
                    }
                )
            }
        }

        else -> TaskListScreen(
            tasks = tasks,
            onTaskClick = ::openTask,
            onToggleCompleted = viewModel::toggleCompleted,
            onAddTask = { route = ROUTE_NEW },
            onSectionChange = ::openSection
        )
    }
}

@Composable
private fun LoadingFallback(onReturn: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = onReturn) {
            Text("Volver a mis tareas")
        }
    }
}

private const val ROUTE_TASKS = "tasks"
private const val ROUTE_CALENDAR = "calendar"
private const val ROUTE_STATISTICS = "statistics"
private const val ROUTE_PROFILE = "profile"
private const val ROUTE_NEW = "new"
private const val ROUTE_DETAIL = "detail"
private const val ROUTE_EDIT = "edit"

private val MAIN_ROUTES = setOf(
    ROUTE_TASKS,
    ROUTE_CALENDAR,
    ROUTE_STATISTICS,
    ROUTE_PROFILE
)
