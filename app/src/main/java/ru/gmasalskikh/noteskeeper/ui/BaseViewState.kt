package ru.gmasalskikh.noteskeeper.ui

import java.lang.IllegalStateException

abstract class BaseViewState<T>(data: T, err: Throwable?) {
    abstract val data: T
    abstract val err: Throwable?
    init {
        if (data == null && err == null) throw IllegalStateException("Data and err property contains null. Only one of them can contains null, data or err property.")
    }
}