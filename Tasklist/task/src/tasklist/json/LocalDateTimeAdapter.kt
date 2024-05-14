package tasklist.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.ToJson
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format

class LocalDateTimeAdapter {
    private val dateTimeFormat = LocalDateTime.Formats.ISO

    @ToJson
    fun toJson(dateTime: LocalDateTime): String {
        return dateTime.format(dateTimeFormat)
    }

    @FromJson
    fun fromJson(dateTimeString: String): LocalDateTime {
        try {
            return LocalDateTime.parse(dateTimeString, dateTimeFormat)
        } catch (e: IllegalArgumentException) {
            throw JsonDataException(e)
        }
    }
}