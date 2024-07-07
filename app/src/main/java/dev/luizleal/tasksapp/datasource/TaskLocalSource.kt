package dev.luizleal.tasksapp.datasource

import dev.luizleal.tasksapp.dao.TaskDao
import dev.luizleal.tasksapp.model.Task

class TaskLocalSource(private val taskDao: TaskDao) {

    fun getAllTasks() = taskDao.getAllTasks()

    fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    fun deleteTask(ids: List<Int>) = taskDao.deleteTasks(ids)

}