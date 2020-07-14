package ru.gmasalskikh.noteskeeper.data.model

sealed class NoteResult {
    data class Success<out T>(val data : T): NoteResult()
    data class Error(val err : Throwable): NoteResult()
}