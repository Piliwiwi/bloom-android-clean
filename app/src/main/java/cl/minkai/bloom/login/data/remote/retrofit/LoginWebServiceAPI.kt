package cl.minkai.bloom.login.data.remote.retrofit

import cl.minkai.bloom.login.data.remote.model.RemoteAuthCredentials
import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginWebServiceAPI {
    @POST("login")
    suspend fun login(@Body request: RemoteUserCredentialsParams): RemoteAuthCredentials
}