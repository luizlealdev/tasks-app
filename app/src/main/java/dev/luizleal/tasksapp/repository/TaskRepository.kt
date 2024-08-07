package dev.luizleal.tasksapp.repository

import dev.luizleal.tasksapp.datasource.TaskLocalSource
import dev.luizleal.tasksapp.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskSource: TaskLocalSource) {

    val allTasks: Flow<List<Task>> = taskSource.getAllTasks()

    fun insertTask(task: Task) = taskSource.insertTask(task)

    suspend fun updateTask(id: Int, title: String, state: Boolean) =
        taskSource.updateTask(id, title, state)

    fun deleteTasks(task: Task) = taskSource.deleteTask(task)

}