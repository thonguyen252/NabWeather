package nguyen.exam.nabweather.models

import java.util.*

/**
 * Create by Nguyen on 03/06/2022
 */
data class WeatherInDay(
    val dt: Date?,
    val sunrise: Date?,
    val sunset: Date?,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>?,
    val speed: Double,
    val temp: WeatherTemperature?,
    val feels_like: WeatherTemperature?,
    val deg: Double,
    val gust: Double,
    val clouds: Double,
    val pop: Double,
    val rain: Double
)
