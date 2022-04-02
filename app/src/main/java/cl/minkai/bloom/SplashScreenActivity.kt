package cl.minkai.bloom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/* Native way cost us to use SDK 31 as min target */

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = MainActivity.makeIntent(this)
        startActivity(intent)
        finish()
    }
}