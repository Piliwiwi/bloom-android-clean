package cl.minkai.network.utils

import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class NetworkErrorHandler @Inject constructor() {
    fun Throwable.toNetworkError(genericMessage: String): NetworkError =
        if (this is HttpException) {
            val jsonError = response()?.errorBody()?.string().orEmpty()
            jsonError.toNetworkError()
        } else {
            NetworkError(genericMessage)
        }

    private fun String.toNetworkError() =
        Gson().fromJson(this, NetworkError::class.java)
}