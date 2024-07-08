package dev.luizleal.tasksapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import dev.luizleal.tasksapp.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * from task_table")
    fun getAllTasks(): Flow<List<Task>>

    @Insert
    fun insertTask(task: Task)

    @Delete
    fun deleteTasks(task: Task)

    @Query("UPDATE task_table SET task_title = :newTitle, is_checked = :newState WHERE id = :id")
    suspend fun updateTask(id: Int, newTitle: String, newState: Boolean)

}