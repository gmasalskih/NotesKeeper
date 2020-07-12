package ru.gmasalskikh.noteskeeper.ui.note_details

import androidx.lifecycle.ViewModel
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.entity.Note
import timber.log.Timber
import java.util.*

class NoteDetailsViewModel(
    private val notesRepository: INotesProvider,
    private val id: String?
) : ViewModel() {

    var note: Note = if (id == null) Note()
    else notesRepository.getNoteById(id) ?: Note()

    fun onTextChangeTitle(title: CharSequence) {
        note = note.copy(
            title = title.toString(),
            lastChanged = Date()
        )
        Timber.i("--- $title")
    }

    fun onTextChangeText(text: CharSequence){
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