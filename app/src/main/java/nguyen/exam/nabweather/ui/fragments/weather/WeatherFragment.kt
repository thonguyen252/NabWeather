package nguyen.exam.nabweather.ui.fragments.weather

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import nguyen.exam.nabweather.R
import nguyen.exam.nabweather.databinding.FragmentWeatherBinding
import nguyen.exam.nabweather.ui.adapters.weather.WeatherInDayRecyclerViewAdapter
import nguyen.exam.nabweather.ui.fragments.BaseFragment

/**
 * Create by Nguyen on 02/06/2022
 */
@AndroidEntryPoint
class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    override val layoutResourceId = R.layout.fragment_weather

    private val viewModel: WeatherFragmentViewModel by viewModels()

    private val adapter by lazy { WeatherInDayRecyclerViewAdapter() }

    override fun setupView() {
        binding.viewModel = viewModel
        binding.weatherAdapter = adapter
        binding.rcvWeather.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun setupViewModel() {
        viewModel.weatherInDay.observe(this) {
            adapter.setData(it)
            adapter.notifyDataSetChanged()
        }
        viewModel.apiErrorLiveData.observe(this) {
            binding.errorMessage = it?.errorMessage
        }
    }
}
