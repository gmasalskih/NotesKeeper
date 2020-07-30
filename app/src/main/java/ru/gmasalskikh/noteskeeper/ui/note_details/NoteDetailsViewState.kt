package ru.gmasalskikh.noteskeeper.ui.note_details

import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.ui.BaseViewState

data class NoteDetailsViewState(override val data: Note? = null, override  val err: Throwable? = null) :
    BaseViewState<Note?>(data = data, err = err)