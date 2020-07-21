package ru.gmasalskikh.noteskeeper.ui.main

import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.ui.BaseViewState

interface MainView {
    fun renderViewState(viewState: BaseViewState<User?>)
    fun navigateToSplashActivity()
}