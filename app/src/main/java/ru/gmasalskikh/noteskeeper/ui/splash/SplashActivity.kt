package ru.gmasalskikh.noteskeeper.ui.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import org.koin.android.ext.android.inject
import ru.gmasalskikh.noteskeeper.R
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.ui.main.MainActivity

class SplashActivity : AppCompatActivity() {

    private val authUI: AuthUI by inject()
    private val notesRepository: NotesRepository by inject()

    companion object {
        private const val RC_SIGN_IN = 0
        fun getIntent(context: Context) = Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }

    override fun onResume() {
        super.onResume()
        notesRepository.getCurrentUser().observe(this, Observer { user ->
            user?.let { startMainActivity() } ?: startLoginActivity()
        })
    }

    private fun startLoginActivity() {
        startActivityForResult(
            authUI.createSignInIntentBuilder()
                .setLogo(R.drawable.android_robot)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
                .build(),
            RC_SIGN_IN
        )
    }

    private fun startMainActivity() {
        startActivity(MainActivity.getIntent(this))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                startMainActivity()
            } else {
                finish()
            }
        }
    }
}