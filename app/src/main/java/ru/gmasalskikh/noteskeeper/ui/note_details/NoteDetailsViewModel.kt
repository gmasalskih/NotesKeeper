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
                setNewViewState(NoteDetailsViewState(data = note))
            }
            is NoteResult.Error -> setNewViewState(NoteDetailsViewState(err = noteResult.err))
        }
    }

    init {
        if (id.isNullOrEmpty())
            setNewViewState(NoteDetailsViewState(data = Note().also { note = it }))
        else notesRepository.getNoteById(id).observeForever(observer)
    }

    fun onTextChangeTitle(title: String) {
        note = note?.copy(title = title, lastChanged = Date())
    }

    fun onTextChangeText(text: String) {
        note = note?.copy(text = text, lastChanged = Date())
    }

    private fun setNewViewState(viewState: NoteDetailsViewState) {
        viewState.data?.let { notesRepository.saveNote(it) }
        this.viewState.value = viewState
    }

    fun saveChanges(){
        setNewViewState(NoteDetailsViewState(data = note))
    }

    override fun onCleared() {
        notesRepository.getNotes().removeObserver(observer)
    }
}