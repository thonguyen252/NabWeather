package nguyen.exam.nabweather.repositories

import android.util.Log
import nguyen.exam.nabweather.services.exeptions.ErrorMessages
import nguyen.exam.nabweather.services.exeptions.NetworkException
import nguyen.exam.nabweather.services.exeptions.ResponseCode
import nguyen.exam.nabweather.services.responses.APIResult
import nguyen.exam.nabweather.services.responses.BaseWeatherAPIResponse
import java.net.UnknownHostException

/**
 * Create by Nguyen on 03/06/2022
 */
abstract class BaseRepository {

    protected suspend fun <R : BaseWeatherAPIResponse> safeApiCall(caller: suspend () -> R): APIResult<R> {
        return try {
            val response = caller.invoke()
            if (response.cod == ResponseCode.SUCCESS) {
                APIResult(true, ResponseCode.SUCCESS, null, response)
            } else {
                APIResult(false, response.cod, null, null)
            }
        } catch (exception: Exception) {
            Log.e("API-Error", exception.toString())
            return when (exception) {
                is UnknownHostException ->
                    APIResult(false, null, exception.message, null)
                is NetworkException ->
                    APIResult(false, null, ErrorMessages.NETWORK_ERROR, null)
                else ->
                    APIResult(false, null, exception.message, null)
            }
        }
    }
}
