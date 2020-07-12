package ru.gmasalskikh.noteskeeper.ui.note_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.entity.Note
import timber.log.Timber
import java.util.*

class NoteDetailsViewModel(
    private val notesRepository: INotesProvider,
    private val id: String?
) : ViewModel() {

    private val _backgroundColor = MutableLiveData<Int>()
    val backgroundColor: LiveData<Int>
        get() = _backgroundColor

    var note: Note = if (id == null) Note()
    else notesRepository.getNoteById(id) ?: Note()

    init {
        _backgroundColor.value = note.color
    }

    fun onTextChangeTitle(title: CharSequence) {
        _backgroundColor.value = note.color
        note = note.copy(
            title = title.toString(),
            lastChanged = Date()
        )
        Timber.i("--- $title")
    }

    fun onTextChangeText(text: CharSequence){
        _backgroundColor.value = note.color

        note = note.copy(
            text = text.toString(),
            lastChanged = Date()
        )
        Timber.i("--- $text")
    }

    override fun onCleared() {
        notesRepository.saveNote(note)
    }
}