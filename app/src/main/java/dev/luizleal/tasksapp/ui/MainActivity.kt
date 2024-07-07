package dev.luizleal.tasksapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dev.luizleal.tasksapp.Application
import dev.luizleal.tasksapp.R
import dev.luizleal.tasksapp.databinding.ActivityMainBinding
import dev.luizleal.tasksapp.model.Task
import dev.luizleal.tasksapp.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: TaskViewModel by viewModels {
       TaskViewModel.TaskViewModelFactory((application as Application).taskRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            fabNewTask.setOnClickListener(setupNewTaskDialog())
        }
    }

    private fun setupNewTaskDialog(): View.OnClickListener {
        return View.OnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.new_note_dialog, null)
            val tasksTitleEditText =
                dialogView.findViewById<TextInputEditText>(R.id.textfield_title)

            MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setPositiveButton(getString(R.string.new_task_positive_button)) { _, _ ->

                    val inputText = tasksTitleEditText.text.toString()

                    if (inputText.isNotEmpty()) {
                        createTask(inputText.trim())
                    }
                }
                .setNegativeButton(getString(R.string.new_task_negative_button)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create().show()
        }
    }

    private fun createTask(title: String) {
        Log.d("Create task", "function called")
        val task = Task(taskTitle = title)
        viewModel.insertTask(task)
    }
}