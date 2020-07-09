package ru.gmasalskikh.noteskeeper.ui.list_notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskikh.noteskeeper.data.entity.Note

class ListNotesViewModel : ViewModel() {

    private val _selectNote = MutableLiveData<Note>()
    val selectNote: LiveData<Note>
        get() = _selectNote


    fun onClickNote(note: Note){
        _selectNote.value = note
    }
}

