package cl.minkai.network.config

object WebServiceConfig {

    object Url {
        const val PROD_HOST = "https://api.minkai.cl:443/api/"
        const val STAG_HOST = "https://api.minkai.cl:443/api/"
        const val DEV_HOST = "https://ec2-18-231-197-107.sa-east-1.compute.amazonaws.com:4000/api/"
        const val DUMMY_HOST = "http://mock.api/"
    }

    object Domain {
        const val PROD = "api.minkai.cl:443"
        const val STAG = "api.minkai.cl:443"
        const val DEV = "ec2-18-231-197-107.sa-east-1.compute.amazonaws.com:4000"
    }

    object Timeout {
        const val CONNECT: Long = 60
        const val READ: Long = 60
    }
}