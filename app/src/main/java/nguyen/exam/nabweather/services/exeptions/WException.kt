package nguyen.exam.nabweather.services.exeptions

/**
 * Create by Nguyen on 03/06/2022
 */
data class WException(val errorCode: String?, val errorMessage: String?) : Exception()
