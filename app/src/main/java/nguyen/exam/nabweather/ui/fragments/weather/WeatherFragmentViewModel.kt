package nguyen.exam.nabweather.ui.fragments.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import nguyen.exam.nabweather.models.WeatherInDay
import nguyen.exam.nabweather.usecases.WeatherUseCases
import nguyen.exam.nabweather.viewmodels.BaseViewModel
import javax.inject.Inject

/**
 * Create by Nguyen on 03/06/2022
 */
@HiltViewModel
class WeatherFragmentViewModel @Inject constructor(private val weatherUseCase: WeatherUseCases) :
    BaseViewModel() {

    companion object {
        private const val KEYWORD_THRESHOLD = 3
    }

    private val _weatherInDayLiveData = MutableLiveData<List<WeatherInDay>>()
    val weatherInDay = _weatherInDayLiveData as LiveData<List<WeatherInDay>>

    private fun getWeather(keyword: String) {
        execute({
            weatherUseCase.getWeather(keyword)
        }, onSuccess = {
            _weatherInDayLiveData.value = it
        }, onError = {
            // Handle error
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
