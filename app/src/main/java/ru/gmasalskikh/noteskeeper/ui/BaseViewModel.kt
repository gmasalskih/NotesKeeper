package ru.gmasalskikh.noteskeeper.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T, S : BaseViewState<T>>: ViewModel() {
    val viewState = MutableLiveData<S>()
    fun getViewState(): LiveData<S> = viewState
}