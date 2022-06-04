package nguyen.exam.nabweather.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import nguyen.exam.nabweather.services.exeptions.WException
import nguyen.exam.nabweather.services.responses.APIResult

/**
 * Create by Nguyen on 02/06/2022
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * Execute an API call with a delay.
     *
     * @param delay time in millisecond.
     *
     */
    fun <T> executeDelay(
        delay: Long,
        executor: suspend () -> APIResult<T>,
        onSuccess: ((T) -> Unit)?,
        onError: ((WException) -> Unit)?
    ): Job {
        return viewModelScope.launch {
            delay(delay)
            execute(executor, onSuccess, onError)
        }
    }

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

    protected fun <T> Flow<T>.asLiveData(scoped: CoroutineScope): LiveData<T> {
        return this.asLiveData(scoped.coroutineContext)
    }
}
