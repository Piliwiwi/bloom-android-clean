package cl.minkai.network.config

object WebServiceConfig {

    object Url {
        const val PROD_HOST = "https://api.minkai.cl:443/api/"
        const val DEV_HOST = ""
        const val DUMMY_HOST = "http://mock.api/"
    }

    object Domain {
        const val PROD = "api.minkai.cl:443"
        const val DEV = ""
    }

    object Timeout {
        const val CONNECT: Long = 60
        const val READ: Long = 60
    }
}