package ru.gmasalskikh.noteskeeper.data.entity

import ru.gmasalskikh.noteskeeper.data.ColorProvider
import java.util.*

data class Note(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val text: String = "",
    val color: Int = ColorProvider.getRandomColor(),
    val lastChanged: Date = Date()
) : Comparable<Note> {
    override fun equals(other: Any?) = when {
        this === other -> true
        other is Note -> id == other.id
        else -> false
    }

    override fun hashCode() = id.hashCode()
    override fun compareTo(other: Note) = when (other) {
        this -> 0
        else -> lastChanged.compareTo(other.lastChanged)
    }
}