package nguyen.exam.nabweather.services.responses.weather

import nguyen.exam.nabweather.models.WeatherInDay
import nguyen.exam.nabweather.services.responses.BaseWeatherAPIResponse

/**
 * Create by Nguyen on 03/06/2022
 */
data class WeatherResponse(
    override val cod: String,
    override val message: String,
    val list: List<WeatherInDay>
) : BaseWeatherAPIResponse
