package ru.gmasalskikh.noteskeeper.ui.splash

import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.ui.BaseViewModel
import ru.gmasalskikh.noteskeeper.utils.observeOnce

class SplashActivityViewModel(
    private val notesRepository: NotesRepository
) : BaseViewModel<User?, SplashViewState>() {

    fun initState() {
        notesRepository.getCurrentUser().observeOnce { user ->
            viewState.value = user?.let { SplashViewState(data = it) } ?: SplashViewState(
                data = null,
                err = Throwable("You have to sign in!")
            )
        }
    }
}