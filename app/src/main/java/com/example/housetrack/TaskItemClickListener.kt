package com.example.housetrack

interface TaskItemClickListener
{
    fun editTaskItem(taskItem: TaskItem)
    fun completeTaskItem(taskItem: TaskItem)
}