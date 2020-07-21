package ru.gmasalskikh.noteskeeper.ui.main

import android.app.Activity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.entity.User
import timber.log.Timber

class MainActivityPresenter(
    private var activity: MainView?,
    private val notesRepository: NotesRepository,
    private val auth: FirebaseAuth,
    private val authUI: AuthUI
) : MainPresenter {
    override fun initViewState() {
        Timber.i("--- initViewState")
        auth.currentUser?.let {
            activity?.renderViewState(
                MainActivityViewSate(
                    data = User(
                        name = it.displayName ?: "",
                        email = it.email ?: "",
                        avatarUri = it.photoUrl?.toString() ?: ""
                    )
                )
            )
        }
    }

    override fun logOut() {
        activity?.let {
            authUI.signOut(it as Activity).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    notesRepository.userLogOut()
                    activity?.navigateToSplashActivity()
                }
            }
        }
    }

    override fun onCleared() {
        activity = null
    }
}