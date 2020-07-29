package ru.gmasalskikh.noteskeeper.ui.list_notes

import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.ui.BaseViewState
import java.lang.IllegalStateException

data class ListNotesViewState(
    override val data: List<Note>? = null,
    override val err: Throwable? = null
) : BaseViewState<List<Note>?>(data = data, err = err) {
    init {
        if (data == null && err == null) throw IllegalStateException("Data and err property contains null")
    }
}