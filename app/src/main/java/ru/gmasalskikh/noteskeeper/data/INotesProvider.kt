package ru.gmasalskikh.noteskeeper.data

import kotlinx.coroutines.channels.ReceiveChannel
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.data.model.Result

interface INotesProvider {
    fun getCurrentUser(): User
    suspend fun getNotes(): ReceiveChannel<Result>
    suspend fun getNoteById(id: String): Result
    suspend fun delNoteById(id: String): Result
    suspend fun saveNote(note: Note): Result
    suspend fun signOut(): Result
}