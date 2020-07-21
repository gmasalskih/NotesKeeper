package ru.gmasalskikh.noteskeeper.ui.main

import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.ui.BaseViewState

data class MainActivityViewSate(
    override val data: User? = null,
    override val err: Throwable? = null
) : BaseViewState<User?>(data = data, err = err)