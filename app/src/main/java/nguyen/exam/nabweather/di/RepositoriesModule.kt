package nguyen.exam.nabweather.di

import dagger.Provides
import nguyen.exam.nabweather.repositories.weather.WeatherRepository
import nguyen.exam.nabweather.repositories.weather.WeatherRepositoryImpl
import nguyen.exam.nabweather.services.WeatherServices

/**
 * Create by Nguyen on 02/06/2022
 */
class RepositoriesModule {

    @Provides
    fun provideWeatherRepository(service: WeatherServices): WeatherRepository {
        return WeatherRepositoryImpl(service)
    }
}