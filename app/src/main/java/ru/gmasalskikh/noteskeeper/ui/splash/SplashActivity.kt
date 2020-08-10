package ru.gmasalskikh.noteskeeper.ui.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.gmasalskikh.noteskeeper.R
import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.ui.BaseActivity
import ru.gmasalskikh.noteskeeper.ui.main.MainActivity
import ru.gmasalskikh.noteskeeper.utils.toToast

class SplashActivity : BaseActivity<User?, SplashViewState>() {

    override val viewModel: SplashActivityViewModel by viewModel()
    private val signInIntent: Intent by inject {
        parametersOf(
            R.drawable.android_robot,
            R.style.LoginStyle
        )
    }

    companion object {
        private const val RC_SIGN_IN = 0
        fun getIntent(context: Context) = Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.initState()
        }
    }

    private fun startLoginActivity() = startActivityForResult(signInIntent, RC_SIGN_IN)

    private fun startMainActivity() = startActivity(MainActivity.getIntent(this))

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) startMainActivity()
            else finish()
        }
    }

    override fun renderData(data: User?) {
        data?.let { startMainActivity() } ?: startLoginActivity()
    }

    override fun renderErr(err: Throwable) {
        getText(R.string.sign_in_warning).toString().toToast(this)
        startLoginActivity()
    }
}