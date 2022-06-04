package nguyen.exam.nabweather.services

import nguyen.exam.nabweather.services.responses.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create by Nguyen on 02/06/2022
 *
 * Test link: https://api.openweathermap.org/data/2.5/forecast/daily?q=saigon&cnt=7&appid=60c6fbeb4b93ac653c492ba806fc346d
 */
interface WeatherServices {

    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("units") unit: String = "metric",
        @Query("q") keyword: String,
        @Query("cnt") count: Int = 7,
        @Query("appid") apiKey: String = "60c6fbeb4b93ac653c492ba806fc346d"
    ): WeatherResponse
}
