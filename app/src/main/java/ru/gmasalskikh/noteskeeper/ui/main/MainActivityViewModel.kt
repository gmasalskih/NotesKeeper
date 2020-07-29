package ru.gmasalskikh.noteskeeper.ui.main

import androidx.lifecycle.Observer
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.ui.BaseViewModel
import timber.log.Timber

class MainActivityViewModel(
    private val notesRepository: NotesRepository
) : BaseViewModel<User?, MainActivityViewSate>() {

    private val observer = Observer<User?> { user ->
        viewState.value =
            user?.let { MainActivityViewSate(data = it) } ?: MainActivityViewSate(
                data = null,
                err = Throwable("You have to sign in!")
            )
    }

    fun initViewState(){
        notesRepository.getCurrentUser().observeForever(observer)
    }

    fun signOut() {
        notesRepository.signOut()
    }

    fun clearObserver(){
        notesRepository.getCurrentUser().removeObserver(observer)
    }

    override fun onCleared() {
        Timber.i("--- onCleared")
        super.onCleared()
        clearObserver()
    }
}