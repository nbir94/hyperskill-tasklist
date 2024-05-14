package tasklist.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import tasklist.json.LocalDateTimeAdapter
import tasklist.model.Task
import java.io.File

private const val TASK_LIST_PATH = "tasklist.json"

@OptIn(ExperimentalStdlibApi::class)
class TaskStorage {
    private val moshi = Moshi.Builder()
        .add(LocalDateTimeAdapter())
        .addLast(KotlinJsonAdapterFactory())
        .build()
    private val taskListAdapter = moshi.adapter<MutableList<Task>>()

    fun saveTasks(taskList: MutableList<Task>) {
        val json = taskListAdapter.toJson(taskList)
        val jsonFile = File(TASK_LIST_PATH)
        jsonFile.writeText(json)
    }

    fun loadTasks(): MutableList<Task> {
        val jsonFile = File(TASK_LIST_PATH)
        if (!jsonFile.exists()) return mutableListOf()
        return taskListAdapter.fromJson(jsonFile.readText()) ?: mutableListOf()
    }
}