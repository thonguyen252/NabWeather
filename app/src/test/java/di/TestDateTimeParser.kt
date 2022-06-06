package di

import com.google.gson.JsonPrimitive
import nguyen.exam.nabweather.services.retrofit.WDateTypeAdapter
import org.junit.Test
import java.util.*

/**
 * Create by Nguyen on 05/06/2022
 *
 * Test retrofit DateTime parser adapter.
 */
class TestDateTimeParser {

    @Test
    fun testDateTimeDeserialize() {
        val timeStamp = 1654401600L
        val jsonElement = JsonPrimitive(timeStamp)

        val expectedDateTime = Calendar.getInstance().apply {
            set(Calendar.DATE, 5)
            set(Calendar.MONTH, Calendar.JUNE)
            set(Calendar.YEAR, 2022)
            set(Calendar.HOUR_OF_DAY, 11)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val dateTime = WDateTypeAdapter().deserialize(jsonElement, null, null)
        assert(expectedDateTime == dateTime)
    }

    @Test
    fun testDateTimeSerialize() {
        val dateTime = Calendar.getInstance().apply {
            set(Calendar.DATE, 6)
            set(Calendar.MONTH, Calendar.JUNE)
            set(Calendar.YEAR, 2030)
            set(Calendar.HOUR_OF_DAY, 4)
            set(Calendar.MINUTE, 49)
            set(Calendar.SECOND, 20)
            set(Calendar.MILLISECOND, 0)
        }.time

        val expectedSerialization = JsonPrimitive(1906926560L)
        val serialization = WDateTypeAdapter().serialize(dateTime, null, null)
        assert(expectedSerialization == serialization)
    }
}
