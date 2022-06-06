package nguyen.exam.nabweather.services.retrofit

import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

/**
 * Create by Nguyen on 06/06/2022
 */
class WDateTypeAdapter : JsonDeserializer<Date>, JsonSerializer<Date> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        return try {
            json?.asLong?.let {
                Date().apply { time = it * 1000 }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    override fun serialize(
        src: Date,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.time.div(1000))
    }
}
