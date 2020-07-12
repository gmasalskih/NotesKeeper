package ru.gmasalskikh.noteskeeper.data

import androidx.lifecycle.LiveData
import ru.gmasalskikh.noteskeeper.data.entity.Note

interface INotesProvider {
    fun saveNote(note: Note)
    fun getNotes(): LiveData<List<Note>>
    fun getNoteById(id: String): Note?
    fun getNoteByIndex(index: Int): Note?
}