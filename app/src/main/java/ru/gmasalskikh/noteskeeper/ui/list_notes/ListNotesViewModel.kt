package ru.gmasalskikh.noteskeeper.ui.list_notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.entity.Note
import timber.log.Timber

class ListNotesViewModel(private val notesRepository: INotesProvider) : ViewModel() {

    private val _selectNote = MutableLiveData<Note?>()
    val selectNote: LiveData<Note?>
        get() = _selectNote

    private val _viewState = MutableLiveData<ListNotesViewState>()
    val viewState: LiveData<ListNotesViewState>
        get() = _viewState

    init {
        notesRepository.getNotes().observeForever { listNotes ->
            _viewState.value = ListNotesViewState(listNotes)
        }
    }

    fun onClickNote(note: Note) {
        _selectNote.value = note
        _selectNote.value = null
    }
}