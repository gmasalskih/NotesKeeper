package ru.gmasalskikh.noteskeeper.data

import ru.gmasalskikh.noteskeeper.data.entity.Note

class NotesRepository(private val provider: INotesProvider) {
    fun getNotes() = provider.subscribeToAllNotes()
    fun getNoteById(id: String) = provider.getNoteById(id)
    fun saveNote(note: Note) = provider.saveNote(note)
    fun getCurrentUser() = provider.getCurrentUser()
    fun delNoteById(id: String) = provider.delNoteById(id)
    fun signOut() = provider.signOut()
}