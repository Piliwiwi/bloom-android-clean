package cl.minkai.bloom

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

/* Native way cost us to use SDK 31 as min target */

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val updateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkForUpdates(UpdateAvailability.UPDATE_AVAILABLE)
    }

    private fun checkForUpdates(type: Int) {
        updateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == type
                && it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                updateManager
                    .startUpdateFlowForResult(it, AppUpdateType.IMMEDIATE, this, REQUEST_CODE)
            } else {
                startApp()
            }
        }.addOnFailureListener {
            Log.e("SplashActivity", "Failed to check for updates: $it")
            startApp()
        }
    }

    private fun startApp() {
        val intent = MainActivity.makeIntent(this)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        checkForUpdates(UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CANCELED) finish()
    }

    companion object {
        const val REQUEST_CODE = 123
    }
}