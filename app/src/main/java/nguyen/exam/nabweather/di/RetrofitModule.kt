package nguyen.exam.nabweather.di

import com.google.gson.Gson
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
        val gson = Gson()
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