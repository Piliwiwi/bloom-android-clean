package cl.minkai.network.security

import cl.minkai.network.config.WebServiceConfig

sealed class PinCertificates(val certificates: HashMap<String, String>) {
    object Prod : PinCertificates(
        certificates = hashMapOf(
            Pair(
                WebServiceConfig.Domain.PROD,
                "sha256/*="
            ),
            Pair(
                WebServiceConfig.Domain.PROD,
                "sha256/*="
            ),
            Pair(
                WebServiceConfig.Domain.PROD,
                "sha256/*="
            )
        )
    )

    object Dev : PinCertificates(
        certificates = hashMapOf(
            Pair(
                WebServiceConfig.Domain.DEV,
                "sha256/*="
            ),
            Pair(
                WebServiceConfig.Domain.DEV,
                "sha256/*="
            ),
            Pair(
                WebServiceConfig.Domain.DEV,
                "sha256/*="
            )
        )
    )
}