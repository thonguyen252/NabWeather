package nguyen.exam.nabweather.services.retrofit

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import nguyen.exam.nabweather.WeatherApplication
import nguyen.exam.nabweather.services.exeptions.NetworkException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Create by Nguyen on 06/06/2022
 */
class NetworkInterceptor: Interceptor {

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
