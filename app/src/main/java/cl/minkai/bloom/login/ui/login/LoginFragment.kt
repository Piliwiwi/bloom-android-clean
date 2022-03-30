package cl.minkai.bloom.login.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cl.minkai.bloom.BuildConfig
import cl.minkai.bloom.databinding.FragmentLoginBinding
import cl.minkai.bloom.login.data.LoginDataRepository
import cl.minkai.bloom.login.data.cache.LoginCacheImpl
import cl.minkai.bloom.login.data.remote.LoginRemoteImpl
import cl.minkai.bloom.login.data.remote.model.UserCredentialsParams
import cl.minkai.bloom.login.data.remote.retrofit.LoginWebServiceAPI
import cl.minkai.network.config.NetworkDependencies
import cl.minkai.network.interceptor.AnyInterceptor
import cl.minkai.network.interceptor.InterceptorParams
import cl.minkai.network.interceptor.TokenInterceptor
import cl.minkai.network.retrofit.WebServiceFactory
import cl.minkai.network.security.PassportTokenManager
import cl.minkai.network.security.TokenManager
import cl.minkai.utils.factory.SharedPreferencesFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (binding == null)
            binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pruebas()
    }

    private fun pruebas() {
        val sharedPreferences = SharedPreferencesFactory.makeSharedPreferencesFactory(
            requireContext(), "login", Context.MODE_PRIVATE
        )
        val tokenManager = PassportTokenManager(sharedPreferences)
        val dependencies = NetworkDependencies(
            interceptorParams = InterceptorParams(
                tokenInterceptor = TokenInterceptor(tokenManager),
                unauthorizedInterceptor = AnyInterceptor()
            ),
            flavorName = "prod",
            isDebug = BuildConfig.DEBUG,
            context = requireContext()
        )
        val webService = WebServiceFactory(
            tClass = LoginWebServiceAPI::class.java,
            context = dependencies.context,
            isDebug = dependencies.isDebug,
            interceptorParams = dependencies.interceptorParams
        ).createWebServiceInstance(dependencies.flavorName)
        val remote = LoginRemoteImpl(webService)

        val cache = LoginCacheImpl(tokenManager)
        val repository = LoginDataRepository(remote, cache)
        CoroutineScope(Dispatchers.IO).launch {
            repository.login(UserCredentialsParams(
                email = "arech.pg@gmail.com",
                password = "1234",
                getHash = true
            )).collect { response ->
                CoroutineScope(Dispatchers.Main).launch {
                    binding?.texto?.text = response.token
                }
            }
        }
    }
}