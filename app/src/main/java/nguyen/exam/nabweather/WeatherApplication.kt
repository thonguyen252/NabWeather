package nguyen.exam.nabweather

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
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
}
