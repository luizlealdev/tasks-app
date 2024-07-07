package dev.luizleal.tasksapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "task_title") val taskTitle: String,
    @ColumnInfo(name = "is_checked") val isChecked: Boolean = false
)
