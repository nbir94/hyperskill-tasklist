package tasklist

import tasklist.service.TaskService

/** Make sure that a kotlinx-datetime version is up to date in build.gradle.
 * This code works fine for 0.6.0-RC */
fun main() {
    with(TaskService()) {
        while (true) {
            println("Input an action (add, print, edit, delete, end):")
            val action = readln().uppercase().trim()
            when (action) {
                "ADD" -> addTask()
                "PRINT" -> printTasks()
                "EDIT" -> editTask()
                "DELETE" -> deleteTask()
                "END" -> close().run {
                    return
                }
                else -> println("The input action is invalid")
            }
        }
    }
}
