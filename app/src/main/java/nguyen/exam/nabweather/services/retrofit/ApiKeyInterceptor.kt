package nguyen.exam.nabweather.services.retrofit

import nguyen.exam.nabweather.config.AppConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Create by Nguyen on 06/06/2022
 */
class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        return chain.request().url.newBuilder().apply {
            addQueryParameter("appid", AppConfig.API_KEY)
        }.build().let { url ->
            chain.request().newBuilder().url(url).build()
        }.let {
            chain.proceed(it)
        }
    }
}
