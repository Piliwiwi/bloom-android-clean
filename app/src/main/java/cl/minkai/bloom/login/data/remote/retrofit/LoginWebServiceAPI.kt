package cl.minkai.bloom.login.data.remote.retrofit

import cl.minkai.bloom.login.data.remote.model.AuthCredentials
import cl.minkai.bloom.login.data.remote.model.UserCredentialsParams
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginWebServiceAPI {
    @POST("login")
    suspend fun login(@Body request: UserCredentialsParams): AuthCredentials
}