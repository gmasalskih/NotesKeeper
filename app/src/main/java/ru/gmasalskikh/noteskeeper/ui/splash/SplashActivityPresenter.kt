package ru.gmasalskikh.noteskeeper.ui.splash

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import ru.gmasalskikh.noteskeeper.R

class SplashActivityPresenter(
    private var activity: SplashView?,
    private val authUI: AuthUI,
    private val auth: FirebaseAuth
) : SplashPresenter {
    override fun initState() {
        val intent = authUI.createSignInIntentBuilder()
            .setLogo(R.drawable.android_robot)
            .setTheme(R.style.LoginStyle)
            .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
            .build()
        auth.currentUser?.let { activity?.startMainActivity() }
            ?: activity?.startLoginActivity(intent)
    }

    override fun onCleared() {
        activity = null
    }
}