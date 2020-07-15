package ru.gmasalskikh.noteskeeper.ui

import androidx.lifecycle.*

abstract class BaseViewModel<T, S : BaseViewState<T>> : ViewModel() {
    val viewState = MutableLiveData<S>()
    fun getViewState(): LiveData<S> = viewState
}