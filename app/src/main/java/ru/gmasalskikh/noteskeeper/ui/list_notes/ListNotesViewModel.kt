package ru.gmasalskikh.noteskeeper.ui.list_notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.data.model.NoteResult
import ru.gmasalskikh.noteskeeper.ui.BaseViewState

class ListNotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    private val _selectNote = MutableLiveData<Note?>()
    val selectNote: LiveData<Note?>
        get() = _selectNote

    private val _viewState = MutableLiveData<BaseViewState<List<Note>?>>()
    val viewState: LiveData<BaseViewState<List<Note>?>>
        get() = _viewState

    private val observer = Observer<NoteResult> {
        when(it){
            is NoteResult.Success<*> -> setNewViewState(ListNotesViewState(data = it.data as? List<Note>))
            is NoteResult.Error -> setNewViewState(ListNotesViewState(err = it.err))
        }
    }

    init { notesRepository.getNotes().observeForever (observer) }

    fun onClickNote(note: Note) {
        _selectNote.value = note
        _selectNote.value = null
    }

    private fun setNewViewState(viewState: BaseViewState<List<Note>?>) {
        _viewState.value = viewState
    }

    override fun onCleared() {
        super.onCleared()
        notesRepository.getNotes().removeObserver(observer)
    }
}