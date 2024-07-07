package dev.luizleal.tasksapp.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import dev.luizleal.tasksapp.R
import dev.luizleal.tasksapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.apply {
            fabNewTask.setOnClickListener(setupNewTaskDialog())
        }
    }

    private fun setupNewTaskDialog(): View.OnClickListener? {
        return View.OnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.new_note_dialog, null)
            val tasksTitleEditText = dialogView.findViewById<TextInputEditText>(R.id.textfield_title)

            MaterialAlertDialogBuilder(this@MainActivity)
                .setView(dialogView)
                .setPositiveButton(getString(R.string.new_task_positive_button)) { _, _ ->
                    val inputText = tasksTitleEditText.text.toString()
                    createTask(inputText)
                }
                .setNegativeButton(getString(R.string.new_task_negative_button)) { dialog, _ ->
                    dialog.dismiss()
                }
                .create().show()
        }
    }

    private fun createTask(title: String) {

    }
}