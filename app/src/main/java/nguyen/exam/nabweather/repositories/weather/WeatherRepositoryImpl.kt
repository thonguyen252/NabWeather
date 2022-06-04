package nguyen.exam.nabweather.repositories.weather

import nguyen.exam.nabweather.repositories.BaseRepository
import nguyen.exam.nabweather.services.WeatherServices
import nguyen.exam.nabweather.services.responses.APIResult
import nguyen.exam.nabweather.services.responses.weather.WeatherResponse

/**
 * Create by Nguyen on 02/06/2022
 */
class WeatherRepositoryImpl(private val service: WeatherServices) : WeatherRepository, BaseRepository() {

    override suspend fun getWeather(keyword: String): APIResult<WeatherResponse> {
        return safeApiCall { service.getWeather(keyword = keyword) }
    }

}
