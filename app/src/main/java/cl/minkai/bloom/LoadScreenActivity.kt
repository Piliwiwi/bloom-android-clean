package cl.minkai.bloom

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import cl.minkai.bloom.databinding.ActivityLoadScreenBinding

class LoadScreenActivity : AppCompatActivity() {
    private var binding: ActivityLoadScreenBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (binding == null) binding = ActivityLoadScreenBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.apply {
            icon.alpha = 0f
            icon
                .animate()
                .setDuration(APPEARANCE_DURATION)
                .alpha(1f)
                .withEndAction {
                    val intent = MainActivity.makeIntent(this@LoadScreenActivity)
                    startActivity(intent)
                    finish()
                }
        }
    }

    companion object {
        const val APPEARANCE_DURATION = 2000L
    }
}