package dev.luizleal.tasksapp

import android.app.Application
import dev.luizleal.tasksapp.database.AppDatabase
import dev.luizleal.tasksapp.datasource.TaskLocalSource
import dev.luizleal.tasksapp.repository.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Application : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database: AppDatabase by lazy { AppDatabase.getDatabase(this)}
    private val movieLocalSource by lazy { TaskLocalSource(database.taskDao()) }
    val taskRepository by lazy { TaskRepository(movieLocalSource) }
}