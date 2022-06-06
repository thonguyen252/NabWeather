package nguyen.exam.nabweather.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nguyen.exam.nabweather.BuildConfig
import nguyen.exam.nabweather.config.AppConfig
import nguyen.exam.nabweather.services.WeatherServices
import nguyen.exam.nabweather.services.retrofit.ApiKeyInterceptor
import nguyen.exam.nabweather.services.retrofit.NetworkInterceptor
import nguyen.exam.nabweather.services.retrofit.WDateTypeAdapter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
        okHttpClient: OkHttpClient,
        dateTypeAdapter: WDateTypeAdapter
    ): Retrofit {
        val gson = GsonBuilder().apply {
            registerTypeAdapter(Date::class.java, dateTypeAdapter)
        }.create()
        val builder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
        return builder.baseUrl(BuildConfig.WEATHER_BASE_URL).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideNetworkInterceptor(): Interceptor {
        return NetworkInterceptor()
    }

    @Provides
    @Singleton
    fun provideDateTypeAdapter(): WDateTypeAdapter {
        return WDateTypeAdapter()
    }

    @Provides
    @Singleton
    fun provideApiKeyInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHTTP(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: Interceptor,
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(AppConfig.API_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(AppConfig.API_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(AppConfig.API_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }
}
