package nguyen.exam.nabweather.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nguyen.exam.nabweather.repositories.weather.WeatherRepository
import nguyen.exam.nabweather.usecases.WeatherUseCases
import nguyen.exam.nabweather.usecases.WeatherUserCasesImpl
import javax.inject.Singleton

/**
 * Create by Nguyen on 03/06/2022
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideWeatherUseCases(
        repo: WeatherRepository
    ) : WeatherUseCases {
        return WeatherUserCasesImpl(repo)
    }
}
