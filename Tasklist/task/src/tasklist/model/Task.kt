package tasklist.model

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class Task(
    var priority: Priority,
    var deadline: LocalDateTime,
    var description: String,
) {
    enum class Field { PRIORITY, DATE, TIME, TASK }

    val dueTag: DueTag
        get() {
            val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            return when {
                currentDate > deadline.date -> DueTag.OVERDUE
                currentDate == deadline.date -> DueTag.TODAY
                else -> DueTag.IN_TIME
            }
        }
}