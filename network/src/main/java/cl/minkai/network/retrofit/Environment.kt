package cl.minkai.network.retrofit

sealed class Environment(val name: String) {
    object Prod : Environment(name = "prod")
    object Stag : Environment(name = "stag")
    object Dev : Environment(name = "dev")
    object Dummmy : Environment(name = "dummy")
}