package com.gcaicedo.tasky.data.repository

import android.content.Context
import com.gcaicedo.tasky.data.model.Priority
import com.gcaicedo.tasky.data.model.Task
import org.json.JSONArray
import org.json.JSONObject

class LocalTaskRepository(context: Context) : TaskRepository {

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun loadTasks(): List<Task> {
        val raw = preferences.getString(KEY_TASKS, null) ?: return emptyList()

        return runCatching {
            val jsonArray = JSONArray(raw)
            buildList {
                for (index in 0 until jsonArray.length()) {
                    val item = jsonArray.getJSONObject(index)
                    add(
                        Task(
                            id = item.getString("id"),
                            title = item.getString("title"),
                            description = item.optString("description"),
                            dueDate = item.getString("dueDate"),
                            priority = runCatching {
                                Priority.valueOf(item.getString("priority"))
                            }.getOrDefault(Priority.MEDIUM),
                            completed = item.optBoolean("completed", false),
                            createdAt = item.optLong("createdAt", System.currentTimeMillis())
                        )
                    )
                }
            }
        }.getOrDefault(emptyList())
    }

    override fun saveTasks(tasks: List<Task>) {
        val jsonArray = JSONArray()

        tasks.forEach { task ->
            jsonArray.put(
                JSONObject().apply {
                    put("id", task.id)
                    put("title", task.title)
                    put("description", task.description)
                    put("dueDate", task.dueDate)
                    put("priority", task.priority.name)
                    put("completed", task.completed)
                    put("createdAt", task.createdAt)
                }
            )
        }

        preferences.edit()
            .putString(KEY_TASKS, jsonArray.toString())
            .apply()
    }

    override fun isOnboardingCompleted(): Boolean {
        return preferences.getBoolean(KEY_ONBOARDING, false)
    }

    override fun setOnboardingCompleted(completed: Boolean) {
        preferences.edit().putBoolean(KEY_ONBOARDING, completed).apply()
    }

    companion object {
        private const val PREFERENCES_NAME = "tasky_preferences"
        private const val KEY_TASKS = "tasks"
        private const val KEY_ONBOARDING = "onboarding_completed"
    }
}
