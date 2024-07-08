package dev.luizleal.tasksapp.ui.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import dev.luizleal.tasksapp.databinding.ItemTaskHolderBinding
import dev.luizleal.tasksapp.model.Task

class TaskListAdapter(
    private val onCheckBoxClicked: (Task) -> Unit,
    private val onMoreActionsIconClicked: (Task, parent: View) -> Unit,
    private val onTextTitleClicked: (Task) -> Unit
) : RecyclerView.Adapter<TaskListAdapter.TaskViewHolder>() {

    private var items: List<Task> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskHolderBinding.inflate(inflater, parent, false)

        return TaskViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        when (holder) {
            is TaskViewHolder -> {
                holder.bind(
                    items[position],
                    onCheckBoxClicked,
                    onMoreActionsIconClicked,
                    onTextTitleClicked
                )
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitTasks(items: List<Task>) {
        this.items = items
        notifyDataSetChanged()
    }

    class TaskViewHolder(private val binding: ItemTaskHolderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val taskTitle = binding.textTaskTitle

        @SuppressLint("ClickableViewAccessibility")
        fun bind(
            task: Task,
            onCheckBoxClicked: (Task) -> Unit,
            onMoreActionsIconClicked: (Task, parent: View) -> Unit,
            onTextTitlePressed: (Task) -> Unit
        ) {
            taskTitle.text = task.taskTitle

            var checkBoxState = MaterialCheckBox.STATE_UNCHECKED
            var paintFlagState = taskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            if (task.isChecked) {
                checkBoxState = MaterialCheckBox.STATE_CHECKED
                paintFlagState = taskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            binding.checkboxTask.checkedState = checkBoxState
            binding.textTaskTitle.paintFlags = paintFlagState


            binding.checkboxTask.setOnClickListener {
                onCheckBoxClicked(task)
            }

            binding.buttonMoreActions.setOnClickListener {
                onMoreActionsIconClicked(task, it)
            }

            taskTitle.setOnClickListener {
                onTextTitlePressed(task)
            }
        }
    }

}
