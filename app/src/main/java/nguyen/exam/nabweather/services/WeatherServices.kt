package nguyen.exam.nabweather.services

import retrofit2.http.GET

/**
 * Create by Nguyen on 02/06/2022
 */
interface WeatherServices {

    @GET("data/2.5/forecast/daily?q=saigon&cnt=7&appid=60c6fbeb4b93ac653c492ba806fc346d")
    suspend fun getWeather()
}