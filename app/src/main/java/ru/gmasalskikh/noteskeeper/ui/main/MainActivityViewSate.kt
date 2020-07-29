package ru.gmasalskikh.noteskeeper.ui.main

import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.ui.BaseViewState
import java.lang.IllegalStateException

data class MainActivityViewSate(
    override val data: User? = null,
    override val err: Throwable? = null
) : BaseViewState<User?>(data = data, err = err) {
    init {
        if (data == null && err == null) throw IllegalStateException("Data and err property contains null")
    }
}