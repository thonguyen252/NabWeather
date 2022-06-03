package nguyen.exam.nabweather.usecases

import nguyen.exam.nabweather.models.WeatherInDay
import nguyen.exam.nabweather.services.responses.APIResult

/**
 * Create by Nguyen on 03/06/2022
 */
interface WeatherUseCases {

    suspend fun getWeather(keyword: String): APIResult<List<WeatherInDay>>
}
