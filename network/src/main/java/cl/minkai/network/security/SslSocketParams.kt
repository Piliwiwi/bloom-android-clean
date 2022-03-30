package cl.minkai.network.security

import android.content.Context

data class SslSocketParams(
    val context: Context,
    val certResId: Int,
    val certPassword: String
)
