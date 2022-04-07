package cl.minkai.uicomponents.groupcomponent.bottomsheets

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.annotation.StringRes
import cl.minkai.uicomponents.R
import cl.minkai.uicomponents.component.buttons.AttrsTitledButton
import cl.minkai.uicomponents.databinding.UiGroupComponentBottomSheetAuthBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.parcelize.Parcelize

data class AttrsAuthBottomSheet(
    @StringRes val title: Int = R.string.you_log_in,
    @StringRes val forgotText: Int = R.string.forgot_my_password,
    @StringRes val buttonText: Int = R.string.log_in,
    val onForgotClick: () -> Unit,
    val onButtonClick: (String, String) -> Unit
)

class AuthBottomSheet(private val attrs: AttrsAuthBottomSheet) : BottomSheetDialogFragment() {
    private var binding: UiGroupComponentBottomSheetAuthBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (binding == null) binding =
            UiGroupComponentBottomSheetAuthBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun getTheme() = R.style.CustomBottomSheetDialog

    override fun onStart() {
        super.onStart()
        render()
    }

    private fun render() = binding?.apply {
        title.text = context?.getString(attrs.title).orEmpty()
        forgotPassword.text = context?.getString(attrs.forgotText).orEmpty()
        forgotPassword.setOnClickListener { attrs.onForgotClick() }

        button.isClickable = !email.hasTypedError()

        button.setAttributes(
            AttrsTitledButton(
                buttonText = context?.getString(attrs.buttonText).orEmpty(),
                onClick = {
                    if (email.hasTypedError().not())
                        attrs.onButtonClick(email.getText(), password.getText())
                }
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}