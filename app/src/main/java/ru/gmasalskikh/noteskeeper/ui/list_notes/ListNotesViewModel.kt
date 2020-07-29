package ru.gmasalskikh.noteskeeper.ui.list_notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.data.model.NoteResult
import ru.gmasalskikh.noteskeeper.ui.BaseViewModel
import ru.gmasalskikh.noteskeeper.utils.observeOnce

class ListNotesViewModel(private val notesRepository: NotesRepository) : BaseViewModel<List<Note>?, ListNotesViewState>() {

    private val _selectNote = MutableLiveData<Note?>()
    val selectNote: LiveData<Note?>
        get() = _selectNote

    private val _delNote = MutableLiveData<Note?>()
    val delNote: LiveData<Note?>
        get() = _delNote

    private val _delNoteMsg = MutableLiveData<String?>()
    val delNoteMsg: LiveData<String?>
        get() = _delNoteMsg

    private val _delNoteErr = MutableLiveData<String?>()
    val delNoteErr: LiveData<String?>
        get() = _delNoteErr

    private val observer = Observer<NoteResult> {
        when(it){
            is NoteResult.Success<*> -> setNewViewState(ListNotesViewState(data = it.data as? List<Note>))
            is NoteResult.Error -> setNewViewState(ListNotesViewState(err = it.err))
        }
    }

    init {
        notesRepository.getNotes().observeForever (observer)
    }

    fun onClickNote(note: Note) {
        _selectNote.value = note
        _selectNote.value = null
    }

    fun onLongClickNote(note: Note){
        _delNote.value = note
        _delNote.value = null
    }

    fun delNote(note: Note)= notesRepository.delNoteById(note.id).observeOnce {
        when(it){
            is NoteResult.Success<*> -> {
                _delNoteMsg.value = note.title
                _delNoteMsg.value = null
            }
            is NoteResult.Error -> {
                _delNoteErr.value = it.err.message
                _delNoteErr.value = null
            }
        }
    }


    private fun setNewViewState(viewState: ListNotesViewState) {
        this.viewState.value = viewState
    }

    override fun onCleared() {
        super.onCleared()
        notesRepository.getNotes().removeObserver(observer)
    }
}