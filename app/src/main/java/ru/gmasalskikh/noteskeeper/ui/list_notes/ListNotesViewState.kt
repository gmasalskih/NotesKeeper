package ru.gmasalskikh.noteskeeper.ui.list_notes

import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.ui.BaseViewState

data class ListNotesViewState(
    override val data: List<Note>? = null,
    override val err: Throwable? = null
) : BaseViewState<List<Note>?>(data = data, err = err)