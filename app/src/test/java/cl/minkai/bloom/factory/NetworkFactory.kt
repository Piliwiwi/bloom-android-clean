package cl.minkai.bloom.factory

import cl.minkai.network.utils.NetworkError
import cl.minkai.testingtools.factory.RandomFactory.generateString

object NetworkFactory {
    fun makeNetworkError() = NetworkError(
        message = generateString()
    )
}