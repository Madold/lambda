package dashboard.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

object TextValidationUtils {
    
    private val POSITIVE_FLOAT_REGEX = Regex("^[+]?([0-9]*[.])?[0-9]+$")
    
    fun isPositiveFloat(value: String): Boolean {
        return value.matches(POSITIVE_FLOAT_REGEX)
    }
    
    fun isPositiveLong(value: String): Boolean {
        return try {
            val number = value.toLong()
            number > 0
        } catch (e: NumberFormatException) {
            false
        }
    }

}

object TextFormattingUtils {

    fun formatDateFromTimestamp(timestamp: Long): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        return format.format(date)
    }

    fun convertToTimestamp(dateString: String): Long {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date: Date = dateFormat.parse(dateString)
        return date.time
    }

}

