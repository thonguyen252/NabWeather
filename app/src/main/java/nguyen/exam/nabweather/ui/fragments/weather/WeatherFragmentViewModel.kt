package nguyen.exam.nabweather.ui.fragments.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import nguyen.exam.nabweather.models.WeatherInDay
import nguyen.exam.nabweather.services.exeptions.WException
import nguyen.exam.nabweather.usecases.WeatherUseCases
import nguyen.exam.nabweather.viewmodels.BaseViewModel
import javax.inject.Inject

/**
 * Create by Nguyen on 03/06/2022
 */
@HiltViewModel
open class WeatherFragmentViewModel @Inject constructor(private val weatherUseCase: WeatherUseCases) :
    BaseViewModel() {

    companion object {
        private const val INPUT_DEBOUNCE_TIME = 250L
        private const val KEYWORD_THRESHOLD = 3
    }

    private var getWeatherJob: Job? = null

    private val _weatherInDayLiveData = MutableLiveData<List<WeatherInDay>>()
    val weatherInDay = _weatherInDayLiveData as LiveData<List<WeatherInDay>>

    private val _apiErrorFlow = MutableStateFlow<WException?>(null)
    val apiErrorLiveData = _apiErrorFlow.asLiveData(viewModelScope)

    private fun getWeather(keyword: String) {
        getWeatherJob?.cancel()
        getWeatherJob = executeDelay(INPUT_DEBOUNCE_TIME, {
            weatherUseCase.getWeather(keyword)
        }, onSuccess = {
            _weatherInDayLiveData.value = it
            _apiErrorFlow.value = null
        }, onError = {
            _apiErrorFlow.value = it
            showEmptyResult()
        })
    }

    fun onKeywordChanged(keyword: CharSequence, start: Int, end: Int, count: Int) {
        if (keyword.length > KEYWORD_THRESHOLD) {
            getWeather(keyword.toString())
        } else {
            showEmptyResult()
        }
    }

    private fun showEmptyResult() {
        _weatherInDayLiveData.value = listOf()
    }
}
