package cl.minkai.bloom.login.data.remote.model

import cl.minkai.bloom.login.data.remote.config.Constants.TOKEN
import com.google.gson.annotations.SerializedName

data class AuthCredentials(
    @SerializedName(TOKEN) val token: String
)
