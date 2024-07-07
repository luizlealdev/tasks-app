package dev.luizleal.tasksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.luizleal.tasksapp.model.Task
import dev.luizleal.tasksapp.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks: LiveData<List<Task>> = repository.allTasks.asLiveData()

    fun insertTask(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(ids: List<Int>) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.deleteTasks(ids)
        }
    }

    class TaskViewModelFactory(private val repository: TaskRepository): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                return TaskViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

}