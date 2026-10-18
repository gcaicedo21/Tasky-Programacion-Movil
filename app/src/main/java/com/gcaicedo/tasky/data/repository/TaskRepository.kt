package com.gcaicedo.tasky.data.repository

import com.gcaicedo.tasky.data.model.Task

interface TaskRepository {
    fun loadTasks(): List<Task>
    fun saveTasks(tasks: List<Task>)
    fun isOnboardingCompleted(): Boolean
    fun setOnboardingCompleted(completed: Boolean)
}
