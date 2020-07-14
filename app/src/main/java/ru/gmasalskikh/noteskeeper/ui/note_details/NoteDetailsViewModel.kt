package ru.gmasalskikh.noteskeeper.ui.note_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.data.model.NoteResult
import ru.gmasalskikh.noteskeeper.ui.BaseViewState

class NoteDetailsViewModel(
    private val notesRepository: NotesRepository,
    private val id: String?
) : ViewModel() {

    private var note: Note? = Note()
    private val _viewState = MutableLiveData<BaseViewState<Note?>>()
    private val observer = Observer<NoteResult> { noteResult ->
        when (noteResult) {
            is NoteResult.Success<*> -> {
                note = noteResult.data as? Note
                setNewViewState(NoteDetailsViewState(data = note))
            }
            is NoteResult.Error -> {
                note = null
                setNewViewState(NoteDetailsViewState(err = noteResult.err))
            }
        }
    }

    init {
        if (id.isNullOrEmpty()) _viewState.value = NoteDetailsViewState(data = Note())
        else notesRepository.getNoteById(id).observeForever(observer)
    }

    fun onTextChangeTitle(title: CharSequence) {
        note = note?.copy(title = title.toString())
    }

    fun onTextChangeText(text: CharSequence) {
        note = note?.copy(text = text.toString())
    }

    fun getViewState(): LiveData<BaseViewState<Note?>> = _viewState

    private fun setNewViewState(viewState: BaseViewState<Note?>) {
        _viewState.value = viewState
    }

    override fun onCleared() {
        note?.let { notesRepository.saveNote(it) }
        notesRepository.getNotes().removeObserver(observer)
    }
}