package nguyen.exam.nabweather

import android.app.Application
import android.content.Context
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import dagger.hilt.android.HiltAndroidApp
import nguyen.exam.nabweather.config.AppConfig
import java.lang.ref.WeakReference

/**
 * Create by Nguyen on 04/06/2022
 */
@HiltAndroidApp
class WeatherApplication : Application() {

    companion object {

        private lateinit var weakInstance: WeakReference<WeatherApplication>

        val globalContext: Context
            get() = weakInstance.get()!!.applicationContext
    }

    init {
        weakInstance = WeakReference(this)
    }

    override fun onCreate() {
        super.onCreate()
        initRemoteConfig()
    }

    private fun initRemoteConfig() {
        val remoteConfig = FirebaseRemoteConfig.getInstance()
        remoteConfig.fetchAndActivate().addOnSuccessListener {
            remoteConfig.getString("_weather_api_key").let {
                AppConfig.API_KEY = it
            }
        }
    }
}
