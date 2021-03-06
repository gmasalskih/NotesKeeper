package ru.gmasalskikh.noteskeeper.ui

import androidx.lifecycle.*

abstract class BaseViewModel<T, S : BaseViewState<T>> : ViewModel() {
    protected val viewState = MutableLiveData<S>()
    fun getViewState(): LiveData<S> = viewState
    protected abstract fun saveViewState(data: T? = null, err:Throwable? = null)
}