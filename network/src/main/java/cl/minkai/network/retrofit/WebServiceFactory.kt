package cl.minkai.network.retrofit

import android.content.Context
import cl.minkai.network.config.ConfigParams
import cl.minkai.network.config.WebServiceConfig
import cl.minkai.network.interceptor.InterceptorParams
import cl.minkai.network.retrofit.exception.InvalidEnvironmentException
import cl.minkai.network.security.PinCertificates
import cl.minkai.network.security.SecurityVerificationParams

class WebServiceFactory<TWebService> constructor(
    private val tClass: Class<TWebService>,
    private val context: Context,
    private val isDebug: Boolean,
    private val interceptorParams: InterceptorParams
) {

    fun createWebServiceInstance(environment: String): TWebService {
        return when (environment) {
            Environment.Dummmy.name -> createLocalWebServiceConfig()
            Environment.Dev.name -> createRemoteWebServiceConfig(
                baseUrl = WebServiceConfig.Url.DEV_HOST,
                hostName = WebServiceConfig.Domain.DEV,
                pinCertificates = PinCertificates.Dev.certificates
            )

            Environment.Stag.name -> createRemoteWebServiceConfig(
                baseUrl = WebServiceConfig.Url.STAG_HOST,
                hostName = WebServiceConfig.Domain.STAG,
                pinCertificates = PinCertificates.Prod.certificates
            )
            Environment.Prod.name -> createRemoteWebServiceConfig(
                baseUrl = WebServiceConfig.Url.PROD_HOST,
                hostName = WebServiceConfig.Domain.PROD,
                pinCertificates = PinCertificates.Prod.certificates
            )
            else -> throw InvalidEnvironmentException("Current environment $environment is not supported")
        }
    }

    private fun createLocalWebServiceConfig(): TWebService =
        LocalWebService<TWebService>().create(
            context = context,
            tClass = tClass,
            hostUrl = WebServiceConfig.Url.DUMMY_HOST
        )

    private fun createRemoteWebServiceConfig(
        baseUrl: String,
        hostName: String,
        pinCertificates: HashMap<String, String>
    ): TWebService {
        val remoteWebServiceParams = RemoteWebServiceParams(
            config = ConfigParams(
                isDebug = isDebug,
                baseUrl = baseUrl
            ),
            interceptor = interceptorParams,
            securityVerification = SecurityVerificationParams(
                bloomHostName = hostName,
                pinCertificates = pinCertificates
            )
        )
        return RemoteWebService<TWebService>().create(
            tClass = tClass,
            remoteWebServiceParams = remoteWebServiceParams
        )
    }
}