package cl.minkai.bloom.login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cl.minkai.bloom.BloomApplication
import cl.minkai.bloom.databinding.FragmentLoginBinding
import cl.minkai.bloom.login.data.LoginDataRepository
import cl.minkai.bloom.login.data.remote.model.RemoteUserCredentialsParams
import cl.minkai.bloom.login.di.DaggerLoginComponent
import cl.minkai.bloom.login.di.FragmentModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null

    @Inject
    lateinit var repository: LoginDataRepository

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
        pruebas()
    }

    private fun pruebas() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.login(
                RemoteUserCredentialsParams(
                    email = "arech.pg@gmail.com",
                    password = "1234",
                    getHash = true
                )
            ).collect { response ->
                CoroutineScope(Dispatchers.Main).launch {
                    binding?.texto?.text = response.token
                }
            }
        }
    }
}