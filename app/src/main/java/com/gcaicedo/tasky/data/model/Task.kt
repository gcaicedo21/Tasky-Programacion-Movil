package com.gcaicedo.tasky.data.model

data class Task(
    val id: String,
    val title: String,
    val description: String,
    val dueDate: String,
    val priority: Priority,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
