package nguyen.exam.nabweather.ui.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Create by Nguyen on 04/06/2022
 */
object DateFormatter {

    fun formatWDate(date: Date?): String {
        return date?.let {
            val pattern = "EEE, dd MMM yyy"
            SimpleDateFormat(pattern, Locale.US).format(it)
        } ?: kotlin.run { "" }
    }
}
