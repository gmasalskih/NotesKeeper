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
    id: String?
) : ViewModel() {

    private var note: Note? = null
    private val _viewState = MutableLiveData<BaseViewState<Note?>>()
    private val observer = Observer<NoteResult> { noteResult ->
        when (noteResult) {
            is NoteResult.Success<*> -> {
                (noteResult.data as? Note)?.let { note = it }
                setNewViewState(NoteDetailsViewState(data = note))
            }
            is NoteResult.Error -> {
                setNewViewState(NoteDetailsViewState(err = noteResult.err))
            }
        }
    }

    init {
        if (id.isNullOrEmpty())
            setNewViewState(NoteDetailsViewState(data = Note().also { note = it }))
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