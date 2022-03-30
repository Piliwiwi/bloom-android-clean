package cl.minkai.network.security

import android.content.Context
import java.io.InputStream
import java.lang.IllegalStateException
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

data class SSLSocketFactoryResult(
    val sslSocketFactory: SSLSocketFactory,
    val x509TrustManager: X509TrustManager
)

object TrustedSslSocketFactory {

    fun createSelfSignedSocketFactory(
        context: Context,
        certResId: Int,
        certPassword: CharArray
    ): SSLSocketFactoryResult {
        val trustedKeyStore: KeyStore = KeyStore.getInstance("BKS")
        val inputStream: InputStream = context.resources.openRawResource(certResId)
        inputStream.use {
            trustedKeyStore.load(it, certPassword)
        }

        val algorithm = TrustManagerFactory.getDefaultAlgorithm()
        val trustManagerFactory: TrustManagerFactory = TrustManagerFactory.getInstance(algorithm)
        trustManagerFactory.init(trustedKeyStore)

        val trustManagers: Array<TrustManager> = trustManagerFactory.trustManagers
        if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
            throw IllegalStateException(
                "Unexpected default trust managers: ${trustManagers.contentToString()}"
            )
        }

        val trustManager: X509TrustManager = trustManagers.first() as X509TrustManager
        val sslContext: SSLContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustManagerFactory.trustManagers, null)

        return SSLSocketFactoryResult(
            sslSocketFactory = sslContext.socketFactory,
            x509TrustManager = trustManager
        )
    }
}