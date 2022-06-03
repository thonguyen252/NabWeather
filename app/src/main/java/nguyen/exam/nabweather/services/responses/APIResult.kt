package nguyen.exam.nabweather.services.responses

/**
 * Create by Nguyen on 03/06/2022
 */
data class APIResult<T>(
    val isSuccess: Boolean,
    val code: String?,
    val message: String?,
    val data: T?
) {

    fun <R> map(mapper: (T?) -> R?): APIResult<R> {
        return APIResult(isSuccess, code, message, mapper.invoke(data))
    }
}