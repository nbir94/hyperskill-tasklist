package tasklist.service

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import tasklist.model.Priority
import tasklist.model.Task

class TaskService {

    private val taskStorage: TaskStorage = TaskStorage()
    private val taskList: MutableList<Task> = taskStorage.loadTasks()

    fun addTask() {
        val priority = readPriority()
        val deadline = readDeadline()
        val description = readTaskDescription()
            ?: return
        taskList.add(Task(priority, deadline, description))
    }

    fun editTask() {
        printTasks()
        if (taskList.isEmpty()) return
        taskList[readTaskIndex()].apply {
            when (readFieldToEdit()) {
                Task.Field.PRIORITY -> priority = readPriority()
                Task.Field.DATE -> deadline = readDate().atTime(deadline.time)
                Task.Field.TIME -> deadline = deadline.date.atTime(readTime())
                Task.Field.TASK -> description = readTaskDescriptionNotNull()
            }
        }
        println("The task is changed")
    }

    fun deleteTask() {
        printTasks()
        if (taskList.isEmpty()) return
        taskList.removeAt(readTaskIndex())
        println("The task is deleted")
    }

    fun printTasks() = tasklist.util.printTasks(taskList)

    fun close() {
        println("Tasklist exiting!")
        taskStorage.saveTasks(taskList)
    }

    private fun readTaskIndex(): Int {
        while (true) {
            println("Input the task number (1-${taskList.size}):")
            readln().trim()
                .toIntOrNull()
                ?.let { it - 1 }
                ?.takeIf { it in taskList.indices }
                ?.let {
                    return it
                }
            println("Invalid task number")
        }
    }

    private fun readPriority(): Priority {
        while (true) {
            println("Input the task priority (C, H, N, L):").runCatching {
                return Priority.fromShortName(readln())
            }
        }
    }

    private fun readDeadline(): LocalDateTime = readDate().atTime(readTime())

    private fun readDate(): LocalDate {
        val readDateFormat = LocalDate.Format {
            year(Padding.NONE)
            char('-')
            monthNumber(Padding.NONE)
            char('-')
            dayOfMonth(Padding.NONE)
        }
        while (true) {
            println("Input the date (yyyy-mm-dd):")
            try {
                return LocalDate.parse(readln().trim(), readDateFormat)
            } catch (_: IllegalArgumentException) {
                println("The input date is invalid")
            }
        }
    }

    private fun readTime(): LocalTime {
        val readTimeFormat = LocalTime.Format {
            hour(Padding.NONE)
            char(':')
            minute(Padding.NONE)
        }
        while (true) {
            println("Input the time (hh:mm):")
            try {
                return LocalTime.parse(readln().trim(), readTimeFormat)
            } catch (_: IllegalArgumentException) {
                println("The input time is invalid")
            }
        }
    }

    private fun readTaskDescription(): String? {
        println("Input a new task (enter a blank line to end):")
        val description = generateSequence { readln().trim() }
            .takeWhile { it.isNotBlank() }
            .joinToString(separator = "\n")
            .takeUnless { it.isEmpty() }
        description ?: println("The task is blank")
        return description
    }

    private fun readTaskDescriptionNotNull(): String {
        while (true) {
            return readTaskDescription() ?: continue
        }
    }

    private fun readFieldToEdit(): Task.Field {
        while (true) {
            println("Input a field to edit (priority, date, time, task):")
            try {
                return Task.Field.valueOf(readln().trim().uppercase())
            } catch (_: IllegalArgumentException) {
                println("Invalid field")
            }
        }
    }
}