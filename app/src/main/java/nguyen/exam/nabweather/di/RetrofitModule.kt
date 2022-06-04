package nguyen.exam.nabweather.di

import android.accounts.NetworkErrorException
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.google.gson.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nguyen.exam.nabweather.BuildConfig
import nguyen.exam.nabweather.WeatherApplication
import nguyen.exam.nabweather.config.AppConfig
import nguyen.exam.nabweather.services.WeatherServices
import nguyen.exam.nabweather.services.exeptions.NetworkException
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideNetworkInterceptor(): Interceptor {
        return NetworkInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHTTP(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(networkInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(AppConfig.API_TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(AppConfig.API_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(AppConfig.API_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }
}

object NetworkInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val connectManager =
            WeatherApplication.globalContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        connectManager?.run {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getNetworkCapabilities(activeNetwork)?.run {
                    when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                        else -> false
                    }.also {
                        if (!it) throw NetworkException()
                    }
                } ?: run {
                    throw NetworkException()
                }
            } else {
                activeNetworkInfo?.run {
                    when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_VPN -> true
                        else -> false
                    }.also {
                        if (!it) throw NetworkException()
                    }
                } ?: run {
                    throw NetworkException()
                }
            }
        }

        return chain.proceed(chain.request())
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
