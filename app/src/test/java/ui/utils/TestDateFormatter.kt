package ui.utils

import nguyen.exam.nabweather.ui.utils.DateFormatter
import org.junit.Test
import java.util.*

/**
 * Create by Nguyen on 05/06/2022
 */
class TestDateFormatter {

    @Test
    fun testDateFormatter() {
        val dateTime = Calendar.getInstance().apply {
            set(Calendar.DATE, 5)
            set(Calendar.MONTH, Calendar.JUNE)
            set(Calendar.YEAR, 2030)
            set(Calendar.HOUR, 4)
            set(Calendar.MINUTE, 49)
            set(Calendar.SECOND, 20)
            set(Calendar.MILLISECOND, 0)
        }.time

        val expectedFormat = "Wed, 05 Jun 2030"
        val format = DateFormatter.formatWDate(dateTime)
        assert(expectedFormat == format)
    }
}
