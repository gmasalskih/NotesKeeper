package ru.gmasalskikh.noteskeeper.ui.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.gmasalskikh.noteskeeper.ui.main.MainActivity

class SplashActivity : AppCompatActivity(), SplashView {

    private val presenter: SplashPresenter by inject { parametersOf(this) }

    companion object {
        private const val RC_SIGN_IN = 0
        fun getIntent(context: Context) = Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) presenter.initState()
    }

    override fun onPause() {
        super.onPause()
        presenter.onCleared()
    }

    override fun startLoginActivity(intent: Intent) = startActivityForResult(intent, RC_SIGN_IN)

    override fun startMainActivity() = startActivity(MainActivity.getIntent(this))

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) startMainActivity()
            else finish()
        }
    }
}