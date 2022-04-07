package cl.minkai.uicomponents.component.inputs

import android.content.Context
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
import android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import cl.minkai.uicomponents.R
import cl.minkai.uicomponents.databinding.UiComponentInputTextBinding
import cl.minkai.utils.validator.EmailValidator.emailValidation
import com.google.android.material.textfield.TextInputLayout.END_ICON_PASSWORD_TOGGLE

data class AttrsInputText(
    val hint: String = "",
    val helperText: String = "",
    val maxLength: Int? = null,
    val onTextChangeEvent: (String) -> Unit = { _ -> },
    val errorHandler: (String) -> String = { _ -> "" }
)

data class TypedError(
    val isError: Boolean = false,
    val message: String = ""
)

class BloomInputText @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentInputTextBinding? = null
    private var typedError: TypedError? = null
    private var finalText = ""

    init {
        if (binding == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentInputTextBinding.inflate(inflater, this)
        }

        initAttributes()
    }

    private fun initAttributes() {
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.BloomInputText)

        try {
            when (styledAttrs.getInt(R.styleable.BloomInputText_ui_input_type, EMAIL)) {
                EMAIL -> setEmailInput()
                PASSWORD -> setPasswordInput()
                else -> setTextInput()
            }
        } finally {
            styledAttrs.recycle()
        }

        initEvents()
    }

    private fun setEmailInput() = binding?.apply {
        input.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        layout.hint = context.getString(R.string.email)
        input.doOnTextChanged { text, _, _, _ ->
            setEmailMatchError(text.toString())
        }
    }

    private fun setEmailMatchError(email: String) = binding?.apply {
        typedError = TypedError(
            isError = !emailValidation(email),
            message = context.getString(R.string.invalid_email)
        )
    }

    private fun setPasswordInput() = binding?.apply {
        input.inputType = TYPE_CLASS_TEXT or TYPE_TEXT_VARIATION_PASSWORD
        layout.endIconMode = END_ICON_PASSWORD_TOGGLE
        layout.hint = context.getString(R.string.password)
    }

    private fun setTextInput() = binding?.apply {
        input.inputType = TYPE_CLASS_TEXT
    }

    private fun initEvents() = binding?.apply {
        input.doAfterTextChanged {
            finalText = it.toString()
            manageErrors(finalText)
        }
    }

    fun setAttributes(attrs: AttrsInputText) {
        setTexts(attrs)
        setEvents(attrs)
    }

    fun getText() = finalText

    fun hasTypedError() = typedError?.isError == true

    private fun setTexts(attrs: AttrsInputText) = binding?.apply {
        if (attrs.hint.isNotEmpty()) layout.hint = attrs.hint
        if (attrs.helperText.isNotEmpty()) layout.helperText = attrs.helperText

    }

    private fun setEvents(attrs: AttrsInputText) = binding?.apply {
        input.doAfterTextChanged {
            finalText = it.toString()
            attrs.onTextChangeEvent(finalText)
            manageErrors(finalText, attrs)
        }
    }

    private fun manageErrors(text: String, attrs: AttrsInputText? = null) = binding?.apply {
        val customErrorMessage = attrs?.errorHandler?.invoke(text) ?: ""
        val customError = customErrorMessage.isNotEmpty()
        val errorByType = typedError?.isError == true

        layout.isErrorEnabled = customError || errorByType
        layout.error = when {
            errorByType -> typedError?.message
            customError -> customErrorMessage
            else -> null
        }
    }

    companion object {
        const val EMAIL = 1
        const val PASSWORD = 2
    }
}