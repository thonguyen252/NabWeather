package nguyen.exam.nabweather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nguyen.exam.nabweather.repositories.weather.WeatherRepository
import nguyen.exam.nabweather.repositories.weather.WeatherRepositoryImpl
import nguyen.exam.nabweather.services.WeatherServices
import javax.inject.Singleton

/**
 * Create by Nguyen on 02/06/2022
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(service: WeatherServices): WeatherRepository {
        return WeatherRepositoryImpl(service)
    }
}
