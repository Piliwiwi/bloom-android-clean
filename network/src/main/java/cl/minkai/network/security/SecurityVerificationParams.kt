package cl.minkai.network.security

data class SecurityVerificationParams(
    val bloomHostName: String,
    val pinCertificates: HashMap<String, String>
)