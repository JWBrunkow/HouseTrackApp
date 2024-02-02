package com.example.housetrack

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.housetrack.databinding.TaskItemCellBinding
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private val context: Context,
    private val binding: TaskItemCellBinding,
    private val clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root)
{
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    fun bindTaskItem(taskItem: TaskItem) {
        binding.name.text = taskItem.name


        binding.completeButton.setImageResource(taskItem.imageResource())
        binding.completeButton.setColorFilter(taskItem.imageColor(context))

        binding.completeButton.setOnClickListener{ clickListener.completeTaskItem(taskItem) }
        binding.taskCellContainer.setOnClickListener{ clickListener.editTaskItem(taskItem) }

        if(taskItem.dueTime != null) binding.dueTime.text = timeFormat.format(taskItem.dueTime)
        else binding.dueTime.text = ""
    }
}