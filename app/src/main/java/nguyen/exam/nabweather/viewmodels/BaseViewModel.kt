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
abstract class BaseViewModel(
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {

    /**
     * Execute an API call with a delay.
     *
     * @param delay time in millisecond.
     *
     */
    protected fun <T> executeDelay(
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

    protected fun <T> execute(
        executor: suspend () -> APIResult<T>,
        onSuccess: ((T) -> Unit)?,
        onError: ((WException) -> Unit)?
    ): Job {
        return viewModelScope.launch {
            withContext(defaultDispatcher) {
                val result = executor.invoke()

                if (result.isSuccess) {
                    result.data?.let { data ->
                        withContext(mainDispatcher) {
                            onSuccess?.invoke(data)
                        }
                    }
                } else {
                    withContext(mainDispatcher) {
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
