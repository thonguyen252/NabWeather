package nguyen.exam.nabweather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nguyen.exam.nabweather.services.exeptions.WException
import nguyen.exam.nabweather.services.responses.APIResult

/**
 * Create by Nguyen on 02/06/2022
 */
abstract class BaseViewModel : ViewModel() {

    fun <T> execute(
        executor: suspend () -> APIResult<T>,
        onSuccess: ((T) -> Unit)?,
        onError: ((WException) -> Unit)?
    ): Job {
        return viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val result = executor.invoke()

                if (result.isSuccess) {
                    result.data?.let { data ->
                        withContext(Dispatchers.Main) {
                            onSuccess?.invoke(data)
                        }
                    }
                }

                if (!result.isSuccess) {
                    withContext(Dispatchers.Main) {
                        onError?.invoke(WException(result.code, result.message))
                    }
                }
            }
        }
    }
}
