package ru.gmasalskikh.noteskeeper.ui.main

import android.app.Activity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.entity.User

class MainActivityPresenter(
    private var activity: MainView?,
    private val notesRepository: NotesRepository,
    private val authUI: AuthUI
) : MainPresenter {

    private val observer = Observer<User?> { user ->
        user?.let { activity?.renderViewState(MainActivityViewSate(data = user)) } ?: logOut()
    }

    override fun initViewState() = notesRepository.getCurrentUser().observeForever(observer)

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
        notesRepository.getCurrentUser().removeObserver(observer)
        activity = null
    }
}