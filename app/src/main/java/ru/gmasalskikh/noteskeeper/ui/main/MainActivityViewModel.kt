package ru.gmasalskikh.noteskeeper.ui.main

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.data.model.Result
import ru.gmasalskikh.noteskeeper.ui.BaseViewModel
import java.lang.Exception

class MainActivityViewModel(
    private val notesRepository: INotesProvider
) : BaseViewModel<User?, MainActivityViewSate>() {

    fun initViewState() {
        try {
            saveViewState(data = notesRepository.getCurrentUser())
        } catch (e: Exception) {
            saveViewState(err = e)
        }
    }

    fun signOut() = viewModelScope.launch {
        notesRepository.signOut().let { result ->
            when (result) {
                is Result.Success<*> -> saveViewState(err = Throwable("User have to sing in"))
                is Result.Error -> saveViewState(err = result.err)
            }
        }
    }

    override fun saveViewState(data: User?, err: Throwable?) {
        viewState.value = MainActivityViewSate(data = data, err = err)
    }
}