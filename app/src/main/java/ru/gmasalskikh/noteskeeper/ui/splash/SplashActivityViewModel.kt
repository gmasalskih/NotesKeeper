package ru.gmasalskikh.noteskeeper.ui.splash

import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.ui.BaseViewModel
import java.lang.Exception

class SplashActivityViewModel(
    private val notesRepository: INotesProvider
) : BaseViewModel<User?, SplashViewState>() {

    fun initState() {
        try {
            saveViewState(data = notesRepository.getCurrentUser())
        } catch (e: Exception) {
            saveViewState(err = e)
        }
    }

    override fun saveViewState(data: User?, err: Throwable?) {
        viewState.value = SplashViewState(data = data, err = err)
    }
}