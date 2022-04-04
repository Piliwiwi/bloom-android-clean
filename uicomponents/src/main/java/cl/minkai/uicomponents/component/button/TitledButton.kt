package cl.minkai.uicomponents.component.button

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import cl.minkai.uicomponents.R
import cl.minkai.uicomponents.databinding.UiComponentTitledButtonBinding

data class AttrsTitledButton(
    val title: String = "",
    val buttonText: String,
    val onClick: () -> Unit
)

class TitledButton @JvmOverloads constructor(
    context: Context,
    val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentTitledButtonBinding? = null

    init {
        if (binding == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentTitledButtonBinding.inflate(inflater, this)
        }

        initAttrs()
    }

    private fun initAttrs() {
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TitledButton)

        try {
            when (styledAttrs.getInt(R.styleable.TitledButton_ui_type, PRIMARY)) {
                PRIMARY -> setPrimaryButton()
                SECONDARY -> setSecondaryButton()
                else -> setPrimaryButton()
            }
        } finally {
            styledAttrs.recycle()
        }
    }

    private fun setPrimaryButton() = binding?.apply {
        button.setBackgroundColor(resources.getColor(R.color.ui_secondary_color_dark))
    }

    private fun setSecondaryButton() = binding?.apply {
        button.setBackgroundColor(resources.getColor(R.color.ui_secondary_color))
    }

    fun setAttributes(attrs: AttrsTitledButton) {
        setTitle(attrs.title)
        setButton(attrs)
    }

    private fun setTitle(text: String) = binding?.apply {
        if (text.isNotEmpty()) {
            title.isVisible = true
            title.text = text
        } else title.isVisible = false
    }

    private fun setButton(attrs: AttrsTitledButton) = binding?.apply {
        button.text = attrs.buttonText
        button.setOnClickListener { attrs.onClick() }
    }

    companion object {
        const val PRIMARY = 1
        const val SECONDARY = 2
    }
}