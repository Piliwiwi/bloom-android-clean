package cl.minkai.network.security

import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class BloomHostNameVerifier constructor(
    private val bloomHostName: String
) : HostnameVerifier {
    override fun verify(hostname: String?, sslSession: SSLSession?): Boolean =
        hostname?.equals(bloomHostName, ignoreCase = true) ?: false
}