package viewmodels

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import nguyen.exam.nabweather.services.exeptions.ResponseCode
import nguyen.exam.nabweather.services.exeptions.WException
import nguyen.exam.nabweather.services.responses.APIResult
import nguyen.exam.nabweather.viewmodels.BaseViewModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

/**
 * Create by Nguyen on 05/06/2022
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TestBaseViewModel {

    private val testDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler())

    @Before
    fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun testExecuteApiSuccess() {
        val response = "OK"
        val executor = suspend {
            APIResult(true, null, null, response)
        }
        val onSuccess: (String) -> Unit = mock()
        val onError: (WException) -> Unit = mock()

        val testViewModel = TestViewModel(testDispatcher)

        testViewModel.testExecute(
            executor,
            onSuccess = onSuccess,
            onError = onError
        )

        verify(onSuccess, times(1)).invoke(response)
        verifyNoInteractions(onError)
    }

    @Test
    fun testExecuteApiError() {
        val executor = suspend {
            APIResult(false, ResponseCode.NOT_FOUND, null, null)
        }
        val onSuccess: (String) -> Unit = mock()
        val onError: (WException) -> Unit = mock()

        val testViewModel = TestViewModel(testDispatcher)

        testViewModel.testExecute(
            executor,
            onSuccess = onSuccess,
            onError = onError
        )

        verifyNoInteractions(onSuccess)
        verify(onError, times(1)).invoke(any())
    }

    open class TestViewModel(dispatcher: CoroutineDispatcher) :
        BaseViewModel(dispatcher, dispatcher) {

        open fun <T> testExecute(
            executor: suspend () -> APIResult<T>,
            onSuccess: ((T) -> Unit)?,
            onError: ((WException) -> Unit)?
        ): Job = execute(executor, onSuccess, onError)
    }
}
