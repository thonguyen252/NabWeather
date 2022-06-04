package nguyen.exam.nabweather.repositories

import nguyen.exam.nabweather.services.exeptions.ResponseCode
import nguyen.exam.nabweather.services.responses.APIResult
import nguyen.exam.nabweather.services.responses.BaseWeatherAPIResponse

/**
 * Create by Nguyen on 03/06/2022
 */
abstract class BaseRepository {

    suspend fun <R : BaseWeatherAPIResponse> safeApiCall(caller: suspend () -> R): APIResult<R> {
        return try {
            val response = caller.invoke()
            if (response.cod == ResponseCode.SUCCESS) {
                APIResult<R>(true, null, null, response)
            } else {
                APIResult(false, response.cod, null, null)
            }
        } catch (exception: Exception) {
            // Todo check type of exception
            APIResult(false, null, exception.message, null)
        }
    }
}
