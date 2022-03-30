package cl.minkai.network.retrofit

import cl.minkai.network.config.ConfigParams
import cl.minkai.network.config.WebServiceConfig
import cl.minkai.network.interceptor.InterceptorParams
import cl.minkai.network.security.BloomHostNameVerifier
import cl.minkai.network.security.SecurityVerificationParams
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.SocketFactory
import javax.net.ssl.HostnameVerifier

data class RemoteWebServiceParams(
    val config: ConfigParams,
    val interceptor: InterceptorParams,
    val securityVerification: SecurityVerificationParams
)

class RemoteWebService<TRetrofitWebService> {

    fun create(
        tClass: Class<TRetrofitWebService>,
        remoteWebServiceParams: RemoteWebServiceParams
    ): TRetrofitWebService {
        val okHttpClient = makeOkHttpClient(
            httpLoggingInterceptor = makeLoggingInterceptor(remoteWebServiceParams.config.isDebug),
            interceptorParams = remoteWebServiceParams.interceptor,
//            hostnameVerifier = makeHostNameVerifier(
//                bloomHostName = remoteWebServiceParams.securityVerification.bloomHostName
//            ),
//            certificatePinner = makeCertificatePinner(
//                remoteWebServiceParams.securityVerification.pinCertificates
//            )
        )
        return createRetrofit(
            okHttpClient = okHttpClient,
            tClass = tClass,
            baseUrl = remoteWebServiceParams.config.baseUrl
        )
    }

    private fun makeOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        interceptorParams: InterceptorParams,
//        hostnameVerifier: HostnameVerifier,
//        certificatePinner: CertificatePinner
    ): OkHttpClient = if (interceptorParams.dynamicBaseUrlInterceptor == null) {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptorParams.tokenInterceptor)
            .addNetworkInterceptor(interceptorParams.unauthorizedInterceptor)
            .socketFactory(SocketFactory.getDefault())
//            .hostnameVerifier(hostnameVerifier)
            .connectTimeout(WebServiceConfig.Timeout.CONNECT, TimeUnit.SECONDS)
//            .certificatePinner(certificatePinner)
            .build()
    } else {
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptorParams.tokenInterceptor)
            .addInterceptor(interceptorParams.dynamicBaseUrlInterceptor)
            .addNetworkInterceptor(interceptorParams.unauthorizedInterceptor)
            .socketFactory(SocketFactory.getDefault())
//            .hostnameVerifier(hostnameVerifier)
            .connectTimeout(WebServiceConfig.Timeout.CONNECT, TimeUnit.SECONDS)
            .readTimeout(WebServiceConfig.Timeout.READ, TimeUnit.SECONDS)
//            .certificatePinner(certificatePinner)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }

    private fun makeHostNameVerifier(bloomHostName: String) = BloomHostNameVerifier(
        bloomHostName = bloomHostName
    )

    private fun makeCertificatePinner(pinCertificates: HashMap<String, String>): CertificatePinner {
        val certificatePinnerBuilder = CertificatePinner.Builder()
        for ((key, value) in pinCertificates) {
            certificatePinnerBuilder.add(key, value)
        }
        return certificatePinnerBuilder.build()
    }

    private fun createRetrofit(
        okHttpClient: OkHttpClient,
        tClass: Class<TRetrofitWebService>,
        baseUrl: String
    ): TRetrofitWebService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(tClass)
}