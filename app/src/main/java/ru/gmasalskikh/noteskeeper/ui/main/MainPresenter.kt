package ru.gmasalskikh.noteskeeper.ui.main

interface MainPresenter {
    fun initViewState()
    fun logOut()
    fun onCleared()
}