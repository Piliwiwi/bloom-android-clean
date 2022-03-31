package cl.minkai.bloom.login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import cl.minkai.bloom.BloomApplication
import cl.minkai.bloom.databinding.FragmentLoginBinding
import cl.minkai.bloom.login.di.DaggerLoginComponent
import cl.minkai.bloom.login.di.FragmentModule
import cl.minkai.bloom.login.presentation.LoginViewModel
import cl.minkai.bloom.login.presentation.login.LoginUIntent
import cl.minkai.bloom.login.presentation.login.LoginUIntent.InitialUIntent
import cl.minkai.bloom.login.presentation.login.LoginUIntent.LoggingUIntent
import cl.minkai.bloom.login.presentation.login.LoginUiEffect
import cl.minkai.bloom.login.presentation.login.LoginUiEffect.InvalidCredentialsUiEffect
import cl.minkai.bloom.login.presentation.login.LoginUiEffect.LoginSucceedUiEffect
import cl.minkai.bloom.login.presentation.login.LoginUiEffect.NetworkErrorUiEffect
import cl.minkai.bloom.login.presentation.login.LoginUiState
import cl.minkai.bloom.login.presentation.login.LoginUiState.LoadingUiState
import cl.minkai.bloom.login.presentation.login.LoginUiState.ShowLoginScreenUiState
import cl.minkai.bloom.login.presentation.login.model.UserCredentials
import cl.minkai.mvi.MviUi
import cl.minkai.mvi.MviUiEffect
import cl.minkai.network.utils.NetworkError
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class LoginFragment : Fragment(),
    MviUi<LoginUIntent, LoginUiState>, MviUiEffect<LoginUiEffect> {
    private var binding: FragmentLoginBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: LoginViewModel by viewModels { viewModelFactory }

    private val userIntents: MutableSharedFlow<LoginUIntent> = MutableSharedFlow()

    private var userToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupInjection()
    }

    private fun setupInjection() {
        val component = (this.activity?.applicationContext as BloomApplication).appComponent
        DaggerLoginComponent.builder()
            .applicationComponent(component)
            .fragmentModule(FragmentModule(parentFragmentManager, lifecycle))
            .build()
            .inject(this)
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
        subscribeToProcessAndObserveStates()
    }

    private fun subscribeToProcessAndObserveStates() {
        viewModel.run {
            processUserIntentsAndObserveUiStates(userIntents())
                .onEach { renderUiStates(it) }.launchIn(lifecycleScope)
            uiEffect().onEach { handleEffect(it) }.launchIn(lifecycleScope)
        }
    }

    override fun userIntents(): Flow<LoginUIntent> = merge(
        initialUserIntent(),
        userIntents.asSharedFlow()
    )

    private fun initialUserIntent() = flow<LoginUIntent> {
        emit(InitialUIntent)
    }

    override fun renderUiStates(uiStates: LoginUiState) {
        when (uiStates) {
            ShowLoginScreenUiState -> showLoginScreen()
            LoadingUiState -> showLoading()
            else -> {
            }
        }
    }

    private fun showLoginScreen() = binding?.apply {
        texto.text = userToken ?: "HOLA BIENVENIDO AL LOGIN :D"
        btnLogin.setOnClickListener {
            emit(
                LoggingUIntent(
                    UserCredentials(
                        email = "arech.pg@gmail.com",
                        password = "14234"
                    )
                )
            )
        }
    }

    private fun emit(intent: LoginUIntent) {
        viewLifecycleOwner.lifecycleScope.launch {
            userIntents.emit(intent)
        }
    }

    private fun showLoading() = binding?.apply {
        texto.text = "LOADING..."
    }

    override fun handleEffect(uiEffect: LoginUiEffect) {
        when (uiEffect) {
            InvalidCredentialsUiEffect -> showInvalidCredentialsSnackBar()
            is NetworkErrorUiEffect -> showNetworkErrorSnackBar(uiEffect.error)
            is LoginSucceedUiEffect -> login(uiEffect.token)
        }
    }

    private fun showInvalidCredentialsSnackBar() = view?.apply {
        Snackbar.make(this, "Credenciales invalidas, tonta", Snackbar.LENGTH_SHORT).show()
    }

    private fun showNetworkErrorSnackBar(error: NetworkError) = view?.apply {
        Snackbar.make(this, error.message, Snackbar.LENGTH_SHORT).show()
    }

    private fun login(token: String) {
        userToken = token
    }
}