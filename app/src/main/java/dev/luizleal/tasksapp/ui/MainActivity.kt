package dev.luizleal.tasksapp.ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import dev.luizleal.tasksapp.Application
import dev.luizleal.tasksapp.R
import dev.luizleal.tasksapp.databinding.ActivityMainBinding
import dev.luizleal.tasksapp.model.Task
import dev.luizleal.tasksapp.ui.adapter.TaskListAdapter
import dev.luizleal.tasksapp.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: TaskViewModel by viewModels {
        TaskViewModel.TaskViewModelFactory((application as Application).taskRepository)
    }

    private val taskListAdapter = TaskListAdapter({ task ->
        changeTaskState(task)
    }, { task, parent ->
        showTaskPopUpMenu(task, parent)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            fabNewTask.setOnClickListener { setupTaskDialog("CREATE", null) }
        }

        binding.recyclerviewTasks.apply {
            adapter = taskListAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel.allTasks.observe(this) { items ->
            taskListAdapter.submitTasks(items)
        }
    }

    private fun createTask(title: String) {
        val task = Task(taskTitle = title)
        viewModel.insertTask(task)

        Snackbar.make(binding.root, getString(R.string.task_created_message), Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun editTask(task: Task) {
        task.id?.let { viewModel.updateTask(it, task.taskTitle, task.isChecked) }
    }

    private fun deleteTask(task: Task) {

        MaterialAlertDialogBuilder(this).setTitle(getString(R.string.delete_task_alert_dialog_title))
            .setMessage("${getString(R.string.delete_task_alert_dialog_message)} ${task.taskTitle}")
            .setPositiveButton(getString(R.string.new_task_positive_button)) { _, _ ->

                viewModel.deleteTask(task)
                Snackbar.make(
                    binding.root,
                    getString(R.string.task_deleted_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                task.id?.let { taskListAdapter.notifyItemRemoved(it) }

            }
            .setNegativeButton(getString(R.string.new_task_negative_button)) { dialog, _ ->
                dialog.dismiss()
            }.create().show()

    }

    private fun changeTaskState(task: Task) {
        task.id?.let { viewModel.updateTask(it, task.taskTitle, !task.isChecked) }
    }

    private fun setupTaskDialog(action: String, task: Task?) {

        val dialogView = layoutInflater.inflate(R.layout.task_dialog, null)
        val tasksTitleEditText =
            dialogView.findViewById<TextInputEditText>(R.id.textfield_title)
        val dialogHeading = dialogView.findViewById<TextView>(R.id.text_new_task_heading)

        if (action == "EDIT" && task != null) {
            dialogHeading.text = getString(R.string.edit_task)
            tasksTitleEditText.setText(task.taskTitle)
        }

        MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setPositiveButton(getString(R.string.new_task_positive_button)) { _, _ ->

                val inputText = tasksTitleEditText.text.toString()

                if (inputText.isNotEmpty()) {
                    when (action) {
                        "CREATE" -> {
                            createTask(inputText.trim())
                        }

                        "EDIT" -> {
                            if (task != null) {
                                editTask(Task(task.id, inputText, task.isChecked))
                            }
                        }
                    }
                }
            }
            .setNegativeButton(getString(R.string.new_task_negative_button)) { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }

    private fun showTaskPopUpMenu(task: Task, parent: View) {
        val popupMenu = PopupMenu(this@MainActivity, parent)

        popupMenu.menuInflater.inflate(R.menu.task_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_edit -> {
                    setupTaskDialog("EDIT", task)
                    true
                }

                R.id.action_delete -> {
                    deleteTask(task)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }
}