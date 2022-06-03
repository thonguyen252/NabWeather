package nguyen.exam.nabweather.di

import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nguyen.exam.nabweather.BuildConfig
import nguyen.exam.nabweather.config.AppConfig
import nguyen.exam.nabweather.services.WeatherServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Create by Nguyen on 02/06/2022
 */
@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideWeatherServices(retrofit: Retrofit): WeatherServices {
        return retrofit.create(WeatherServices::class.java)
    }

    @Provides
    @Singleton
    fun profileWeatherRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val gson = GsonBuilder().apply {
            registerTypeAdapter(Date::class.java, WDateTypeAdapter)
        }.create()
        val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
        return builder.baseUrl(BuildConfig.WEATHER_BASE_URL).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideOkHTTP(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(AppConfig.API_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(AppConfig.API_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(AppConfig.API_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }
}

object WDateTypeAdapter : JsonDeserializer<Date>, JsonSerializer<Date> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        return try {
            json?.asLong?.let {
                Date().apply { time = it }
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
