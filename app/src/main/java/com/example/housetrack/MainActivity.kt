package com.example.housetrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.housetrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TaskItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        setupBottomNavigation()
        setRecyclerView(TaskType.ACTIVE)


        binding.newTaskButton.setOnClickListener {
            NewTaskSheet(null).show(supportFragmentManager, "newTaskTag")
        }
    }


    private fun setupBottomNavigation() {
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.active -> setRecyclerView(TaskType.ACTIVE)
                R.id.completed -> setRecyclerView(TaskType.COMPLETED)
                R.id.recurring -> setRecyclerView(TaskType.RECURRING)
            }
            true
        }
    }

    private fun setRecyclerView(taskType: TaskType) {
        taskViewModel.taskItems.observe(this) { allTasks ->
            val filteredTasks = when (taskType) {
                TaskType.ACTIVE -> allTasks.filter { !it.isCompleted() }
                TaskType.COMPLETED -> allTasks.filter { it.isCompleted() }
                TaskType.RECURRING -> allTasks.filter { it.isRecurring }
            }
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(filteredTasks, this@MainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        NewTaskSheet(taskItem).show(supportFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }
}

enum class TaskType {
    ACTIVE, COMPLETED, RECURRING
}
