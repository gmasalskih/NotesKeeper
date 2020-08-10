package ru.gmasalskikh.noteskeeper.ui.list_notes

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.data.model.Result
import ru.gmasalskikh.noteskeeper.ui.BaseViewModel

class ListNotesViewModel(private val notesRepository: INotesProvider) :
    BaseViewModel<List<Note>?, ListNotesViewState>() {

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

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        for (result in notesRepository.getNotes()) {
            when (result) {
                is Result.Success<*> -> saveViewState(data = result.data as? List<Note>)
                is Result.Error -> saveViewState(err = result.err)
            }
        }
    }

    fun onClickNote(note: Note) {
        _selectNote.value = note
        _selectNote.value = null
    }

    fun onLongClickNote(note: Note) {
        _delNote.value = note
        _delNote.value = null
    }

    fun delNote(note: Note) = viewModelScope.launch {
        notesRepository.delNoteById(note.id).let { result ->
            when (result) {
                is Result.Success<*> -> {
                    _delNoteMsg.value = note.title
                    _delNoteMsg.value = null
                }
                is Result.Error -> {
                    _delNoteErr.value = result.err.message
                    _delNoteErr.value = null
                }
            }
        }
    }

    override fun saveViewState(data: List<Note>?, err: Throwable?) {
        viewState.value = ListNotesViewState(data = data, err = err)
    }
}