package ru.gmasalskikh.noteskeeper.ui.note_details

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.data.model.Result
import ru.gmasalskikh.noteskeeper.ui.BaseViewModel
import java.util.*

class NoteDetailsViewModel(
    private val notesRepository: INotesProvider,
    private val id: String?
) : BaseViewModel<Note?, NoteDetailsViewState>() {

    private var note: Note? = null

    init {
        initViewState()
    }

    private fun initViewState() = viewModelScope.launch {
        if (id.isNullOrEmpty()) saveViewState(data = Note().also { note = it })
        else notesRepository.getNoteById(id).let { result ->
            when (result) {
                is Result.Success<*> -> saveViewState(data = (result.data as? Note).also {
                    note = it
                })
                is Result.Error -> saveViewState(err = result.err)
            }
        }
    }

    fun onTextChangeTitle(title: String) {
        note = note?.copy(title = title, lastChanged = Date())
    }

    fun onTextChangeText(text: String) {
        note = note?.copy(text = text, lastChanged = Date())
    }

    fun onColorNoteChange(color: Int) {
        note = note?.copy(color = color, lastChanged = Date())
        saveViewState(data = note)
    }

    fun delNote() = viewModelScope.launch {
        note?.let { data ->
            notesRepository.delNoteById(data.id).let { result ->
                when (result) {
                    is Result.Success<*> -> saveViewState(data = Note().also { note = it })
                    is Result.Error -> saveViewState(err = result.err)
                }
            }
        }
    }

    fun saveChanges() = viewModelScope.launch {
        note?.let { if (!it.isEmpty()) notesRepository.saveNote(it) }
    }

    override fun saveViewState(data: Note?, err: Throwable?) {
        viewState.value = NoteDetailsViewState(data = data, err = err)
    }
}