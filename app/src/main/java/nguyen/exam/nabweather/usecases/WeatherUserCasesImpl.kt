package nguyen.exam.nabweather.usecases

import nguyen.exam.nabweather.models.WeatherInDay
import nguyen.exam.nabweather.repositories.weather.WeatherRepository
import nguyen.exam.nabweather.services.responses.APIResult

/**
 * Create by Nguyen on 03/06/2022
 */
class WeatherUserCasesImpl(private val repo: WeatherRepository) : WeatherUseCases {

    override suspend fun getWeather(keyword: String): APIResult<List<WeatherInDay>> {
        return repo.getWeather(keyword).map { it?.list }
    }
}
