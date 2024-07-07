package dev.luizleal.tasksapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import dev.luizleal.tasksapp.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * from task_table")
    fun getAllTasks(): Flow<List<Task>>

    @Insert
    fun insertTask(task: Task)

    @Query("DELETE FROM task_table WHERE id IN (:ids)")
    fun deleteTasks(ids: List<Int>)

    @Update
    suspend fun updateTask(task: Task)

}