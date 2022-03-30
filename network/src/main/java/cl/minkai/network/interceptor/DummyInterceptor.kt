package cl.minkai.network.interceptor

import android.content.Context
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.lang.Thread.sleep
import java.net.URI
import java.util.Locale
import okio.Buffer

class DummyInterceptor(private val context: Context) : Interceptor {

    private val tag = this.javaClass.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        sleep(DEFAULT_SLEEP_TIME)
        val uri = chain.request().url.toUri()
        val httpMethod = chain.request().method.lowercase(Locale.getDefault())
        logHttpMethodAndUri(httpMethod, uri)
        logRequestBody(chain)
        val fileName = getFileNameWithHttpMethodAndExtension(chain, httpMethod)
        val filePath = getFilePathWithHost(uri, fileName)
        logFilePath(filePath)

        val responseCode = getResponseHeaderCode(filePath)
        val jsonBodyText = getJsonBodyText(filePath)
        logResponseBody(jsonBodyText)

        return makeResponseBuilder(responseCode, jsonBodyText, chain).build()
    }

    private fun logHttpMethodAndUri(method: String, uri: URI) {
        Log.d(tag, "--> Request url: [${method.lowercase(Locale.getDefault())}]$uri")
    }

    private fun logRequestBody(chain: Interceptor.Chain) {
        Log.d(tag, "Request Body: ${bodyToString(chain.request())}")
    }

    private fun bodyToString(request: Request): String {
        val requestBuilder = request.newBuilder().build()
        val buffer = Buffer()
        requestBuilder.body?.writeTo(buffer)
        return buffer.readUtf8()
    }

    private fun getFileNameWithHttpMethodAndExtension(
        chain: Interceptor.Chain,
        httpMethod: String
    ): String {
        val fileName = chain.request().url.pathSegments.last()
        return fileName + "_$httpMethod" + FILE_EXTENSION
    }

    private fun getFilePathWithHost(uri: URI, fileName: String): String {
        val path = uri.path.substring(0, uri.path.lastIndexOf('/') + 1)
        return uri.host + path.lowercase(Locale.getDefault()) + fileName
    }

    private fun logFilePath(filePath: String) {
        Log.d(tag, "Read data from file : $filePath")
    }

    private fun getResponseHeaderCode(filePath: String): Int =
        if (filePath == AUTHORIZATION_CHALLENGE_PATH) {
            CREATED_CODE
        } else {
            SUCCESS_CODE
        }

    private fun getJsonBodyText(filePath: String): String = context
        .assets
        .open(filePath)
        .bufferedReader()
        .use { it.readText() }

    private fun logResponseBody(jsonBodyText: String) {
        Log.d(tag, "Response: $jsonBodyText")
    }

    private fun makeResponseBuilder(
        responseCode: Int,
        jsonBodyText: String,
        chain: Interceptor.Chain
    ) = Response.Builder()
        .code(responseCode)
        .message(jsonBodyText)
        .request(chain.request())
        .protocol(Protocol.HTTP_1_0)
        .body(
            jsonBodyText
                .toByteArray()
                .toResponseBody(CONTENT_TYPE_APPLICATION_JSON.toMediaTypeOrNull())
        )
        .addHeader(LOCATION, LOCATION_LOGIN)
        .addHeader(CONTENT_TYPE, CONTENT_TYPE_APPLICATION_JSON)

    companion object {
        private const val DEFAULT_SLEEP_TIME = 1000L

        private const val FILE_EXTENSION = ".json"
        private const val AUTHORIZATION_CHALLENGE_PATH =
            "mock.api/authorization/challenge_post.json"
        private const val CONTENT_TYPE = "content-type"
        private const val CONTENT_TYPE_APPLICATION_JSON = "application/json;charset=UTF-8"
        private const val CREATED_CODE = 201
        private const val SUCCESS_CODE = 200
        private const val LOCATION = "Location"
        private const val LOCATION_LOGIN = "bloom://login?id=token"
    }
}