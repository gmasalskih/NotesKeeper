package ru.gmasalskikh.noteskeeper.ui.splash

import android.content.Intent

interface SplashView {
    fun startLoginActivity(intent: Intent)
    fun startMainActivity()
}