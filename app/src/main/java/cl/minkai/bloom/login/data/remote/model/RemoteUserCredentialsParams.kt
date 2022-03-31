package cl.minkai.bloom.login.data.remote.model

import cl.minkai.bloom.login.data.remote.config.Constants.EMAIL
import cl.minkai.bloom.login.data.remote.config.Constants.GET_HASH
import cl.minkai.bloom.login.data.remote.config.Constants.PASSWORD
import com.google.gson.annotations.SerializedName

data class RemoteUserCredentialsParams(
    @SerializedName(EMAIL) val email: String,
    @SerializedName(PASSWORD) val password: String,
    @SerializedName(GET_HASH) val getHash: Boolean
)
