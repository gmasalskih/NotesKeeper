package ru.gmasalskikh.noteskeeper.ui.note_details

import androidx.lifecycle.Observer
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.data.model.NoteResult
import ru.gmasalskikh.noteskeeper.ui.BaseViewModel
import java.util.*

class NoteDetailsViewModel(
    private val notesRepository: NotesRepository,
    id: String?
) : BaseViewModel<Note?, NoteDetailsViewState>() {

    private var note: Note? = null
    private val observer = Observer<NoteResult> { noteResult ->
        when (noteResult) {
            is NoteResult.Success<*> -> {
                (noteResult.data as? Note)?.let { note = it }
                setNewViewState(data = note)
            }
            is NoteResult.Error -> setNewViewState(err = noteResult.err)
        }
    }

    init {
        if (id.isNullOrEmpty()) setNewViewState(data = Note().also { note = it })
        else notesRepository.getNoteById(id).observeForever(observer)
    }

    fun onTextChangeTitle(title: String) {
        note = note?.copy(title = title, lastChanged = Date())
    }

    fun onTextChangeText(text: String) {
        note = note?.copy(text = text, lastChanged = Date())
    }

    fun onColorNoteChange(color: Int) {
        note = note?.copy(color = color, lastChanged = Date())
        setNewViewState(data = note)
    }

    private fun setNewViewState(data: Note? = null, err: Throwable? = null) {
        this.viewState.value = NoteDetailsViewState(data = data, err = err)
    }

    fun delNote() {
        note?.let { notesRepository.delNoteById(it.id) }
        note = Note()
        setNewViewState(data = note)
    }

    fun saveChanges() = note?.let { if (!it.isEmpty()) notesRepository.saveNote(it) }

    override fun onCleared() = notesRepository.getNotes().removeObserver(observer)
}