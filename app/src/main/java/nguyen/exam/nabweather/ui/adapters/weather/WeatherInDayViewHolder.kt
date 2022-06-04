package nguyen.exam.nabweather.ui.adapters.weather

import nguyen.exam.nabweather.databinding.ItemWeatherInDayBinding
import nguyen.exam.nabweather.models.WeatherInDay
import nguyen.exam.nabweather.ui.adapters.BaseRecyclerViewHolder

/**
 * Create by Nguyen on 03/06/2022
 */
class WeatherInDayViewHolder(override val binding: ItemWeatherInDayBinding) :
    BaseRecyclerViewHolder<WeatherInDay>(binding) {

    override fun bindData(data: WeatherInDay) {
        binding.data = data
    }
}
