package tasklist.util

import tasklist.model.Color
import tasklist.model.Task

private const val TEXT_WIDTH    = 44
private const val ROW_HEADER    = "| N  |    Date    | Time  | P | D |                   Task                     |"
private const val ROW_SEPARATOR = "+----+------------+-------+---+---+--------------------------------------------+"
private const val TASK_START    = "| %-3d| %s | %s | %s | %s |%-${TEXT_WIDTH}s|"
private const val TASK_RESIDUE  = "|    |            |       |   |   |%-${TEXT_WIDTH}s|"

fun printTasks(taskList: List<Task>) {
    if (taskList.isEmpty()) {
        println("No tasks have been input")
        return
    }
    val output = StringBuilder()
        .appendLine(ROW_SEPARATOR)
        .appendLine(ROW_HEADER)
        .appendLine(ROW_SEPARATOR)
    for ((taskIndex, task) in taskList.withIndex()) {
        task.description.split("\n")
            .flatMap { it.chunked(TEXT_WIDTH) }
            .mapIndexed { textIndex, text -> prepareTaskRow(task, taskIndex, isFirstRow = textIndex == 0, text) }
            .forEach(output::appendLine)
        output.appendLine(ROW_SEPARATOR)
    }
    println(output)
}

private fun prepareTaskRow(
    task: Task,
    taskIndex: Int,
    isFirstRow: Boolean,
    text: String,
): String {
    return if (isFirstRow) {
        TASK_START.format(
            taskIndex + 1,
            task.deadline.date,
            task.deadline.time,
            getColoredSpace(task.priority.color),
            getColoredSpace(task.dueTag.color),
            text
        )
    } else {
        TASK_RESIDUE.format(text)
    }
}

private fun getColoredSpace(color: Color) = "\u001B[${color}m \u001B[${Color.DEFAULT}m"
