package ru.gmasalskikh.noteskeeper.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.gmasalskikh.noteskeeper.data.entity.Note
import java.util.*

object LocalNotesRepository : INotesProvider {

    private val notes = MutableLiveData<List<Note>>()

    private val notesList = sortedSetOf(
        Note(
            title = "Firs note",
            text = "Text of first note"
        )
    )

    init {
        notes.value = notesList.toList()
    }

    override fun saveNote(note: Note) {
        notesList.replaceElement(note)
        notes.value = notesList.toList()
    }

    override fun getNotes(): LiveData<List<Note>> = notes

    override fun getNoteById(id: String) = notesList.firstOrNull { note -> note.id == id }

    override fun getNoteByIndex(index: Int) = notesList.elementAtOrNull(index)

}

internal fun TreeSet<Note>.replaceElement(note: Note) {
    this.remove(note)
    this.add(note)
}