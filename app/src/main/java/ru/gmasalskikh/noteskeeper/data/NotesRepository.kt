package ru.gmasalskikh.noteskeeper.data

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.data.model.FirestoreProvider

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
class NotesRepository(private val provider: FirestoreProvider) : INotesProvider {
    override fun getCurrentUser() = provider.getCurrentUser()
    override suspend fun getNotes() = provider.getUserNotes()
    override suspend fun getNoteById(id: String) = provider.getNoteById(id = id)
    override suspend fun delNoteById(id: String) = provider.deleteNote(id = id)
    override suspend fun saveNote(note: Note) = provider.saveNote(note = note)
    override suspend fun signOut() = provider.singOut()
}