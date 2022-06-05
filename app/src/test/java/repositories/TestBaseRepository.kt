package repositories

import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import nguyen.exam.nabweather.repositories.BaseRepository
import nguyen.exam.nabweather.services.exeptions.ErrorMessages
import nguyen.exam.nabweather.services.exeptions.NetworkException
import nguyen.exam.nabweather.services.exeptions.ResponseCode
import nguyen.exam.nabweather.services.responses.BaseWeatherAPIResponse
import nguyen.exam.nabweather.services.responses.weather.WeatherResponse
import org.junit.Test
import java.lang.Exception
import java.net.UnknownHostException

/**
 * Create by Nguyen on 05/06/2022
 */
class TestBaseRepository {

    @Test
    fun testSafeApiCallSuccess() {
        val dumApiCall = suspend {
            delay(500)
            dumSuccessResponse
        }

        runTest {
            val apiResult = testRepo.testSafeApiCall(dumApiCall)
            assert(apiResult.isSuccess)
            assert(apiResult.code == ResponseCode.SUCCESS)
            assert(apiResult.data == dumSuccessResponse)
        }
    }

    @Test
    fun testSafeApiCallNotFound() {
        val dumApiCall = suspend {
            delay(500)
            dumNotFoundResponse
        }

        runTest {
            val apiResult = testRepo.testSafeApiCall(dumApiCall)
            assert(!apiResult.isSuccess)
            assert(apiResult.code == ResponseCode.NOT_FOUND)
            assert(apiResult.data == null)
        }
    }

    @Test
    fun testSafeApiCallException() {

        val unKnowHostException = "Un-know host"
        val commonException = "There is an error"
        var dumApiCall = suspend {
            delay(500)
            throw UnknownHostException(unKnowHostException)
        }

        runTest {
            val apiResult = testRepo.testSafeApiCall(dumApiCall)
            assert(!apiResult.isSuccess)
            assert(apiResult.code == null)
            assert(apiResult.message == unKnowHostException)
        }

        dumApiCall = suspend {
            delay(500)
            throw NetworkException()
        }

        runTest {
            val apiResult = testRepo.testSafeApiCall(dumApiCall)
            assert(!apiResult.isSuccess)
            assert(apiResult.code == null)
            assert(apiResult.message == ErrorMessages.NETWORK_ERROR)
        }

        dumApiCall = suspend {
            delay(500)
            throw Exception(commonException)
        }

        runTest {
            val apiResult = testRepo.testSafeApiCall(dumApiCall)
            assert(!apiResult.isSuccess)
            assert(apiResult.code == null)
            assert(apiResult.message == commonException)
        }
    }

    private val testRepo = object : BaseRepository() {

        suspend fun <R : BaseWeatherAPIResponse> testSafeApiCall(caller: suspend () -> R) =
            safeApiCall(caller)
    }

    private val dumSuccessResponse = WeatherResponse("200", "ok", listOf())

    private val dumNotFoundResponse = WeatherResponse("404", "City not found", listOf())
}
