package cl.minkai.bloom.login.data.remote.model

import cl.minkai.bloom.login.data.remote.config.Constants.TOKEN
import com.google.gson.annotations.SerializedName

data class RemoteAuthCredentials(
    @SerializedName(TOKEN) val token: String
)
