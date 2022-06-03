package nguyen.exam.nabweather.ui.adapters.weather

import androidx.databinding.ViewDataBinding
import nguyen.exam.nabweather.R
import nguyen.exam.nabweather.databinding.ItemWeatherInDayBinding
import nguyen.exam.nabweather.models.WeatherInDay
import nguyen.exam.nabweather.ui.adapters.BaseBindingRecyclerViewAdapter

/**
 * Create by Nguyen on 03/06/2022
 */
class WeatherInDayRecyclerViewAdapter :
    BaseBindingRecyclerViewAdapter<WeatherInDay, WeatherInDayViewHolder>() {

    override val layoutResourceId: Int = R.layout.item_weather_in_day

    override fun createViewHolder(dataBinding: ViewDataBinding) =
        WeatherInDayViewHolder(dataBinding as ItemWeatherInDayBinding)
}
