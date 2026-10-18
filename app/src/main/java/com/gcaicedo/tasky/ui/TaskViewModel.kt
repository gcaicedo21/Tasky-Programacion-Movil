package com.gcaicedo.tasky.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.gcaicedo.tasky.data.model.Priority
import com.gcaicedo.tasky.data.model.Task
import com.gcaicedo.tasky.data.repository.LocalTaskRepository
import com.gcaicedo.tasky.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository = LocalTaskRepository(application)

    private val _tasks = MutableStateFlow(repository.loadTasks().sortedForDisplay())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _onboardingCompleted = MutableStateFlow(repository.isOnboardingCompleted())
    val onboardingCompleted: StateFlow<Boolean> = _onboardingCompleted.asStateFlow()

    fun completeOnboarding() {
        repository.setOnboardingCompleted(true)
        _onboardingCompleted.value = true
    }

    fun addTask(
        title: String,
        description: String,
        dueDate: String,
        priority: Priority
    ) {
        val newTask = Task(
            id = UUID.randomUUID().toString(),
            title = title.trim(),
            description = description.trim(),
            dueDate = dueDate,
            priority = priority
        )
        updateTasks(_tasks.value + newTask)
    }

    fun updateTask(
        id: String,
        title: String,
        description: String,
        dueDate: String,
        priority: Priority
    ) {
        updateTasks(
            _tasks.value.map { task ->
                if (task.id == id) {
                    task.copy(
                        title = title.trim(),
                        description = description.trim(),
                        dueDate = dueDate,
                        priority = priority
                    )
                } else {
                    task
                }
            }
        )
    }

    fun toggleCompleted(id: String) {
        updateTasks(
            _tasks.value.map { task ->
                if (task.id == id) task.copy(completed = !task.completed) else task
            }
        )
    }

    fun deleteTask(id: String) {
        updateTasks(_tasks.value.filterNot { it.id == id })
    }

    fun findTask(id: String?): Task? {
        return id?.let { wantedId -> _tasks.value.firstOrNull { it.id == wantedId } }
    }

    private fun updateTasks(tasks: List<Task>) {
        val sorted = tasks.sortedForDisplay()
        _tasks.value = sorted
        repository.saveTasks(sorted)
    }
}

private fun List<Task>.sortedForDisplay(): List<Task> {
    return sortedWith(
        compareBy<Task> { it.completed }
            .thenBy { it.dueDate }
            .thenByDescending { it.priority.ordinal }
            .thenBy { it.createdAt }
    )
}
