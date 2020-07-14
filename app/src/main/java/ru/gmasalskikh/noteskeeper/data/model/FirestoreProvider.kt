package ru.gmasalskikh.noteskeeper.data.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.entity.Note

class FirestoreProvider(private val store: FirebaseFirestore) : INotesProvider {

    companion object {
        private const val NOTES_COLLECTION = "notes"
    }

    private val notesReference = store.collection(NOTES_COLLECTION)
    private val listNotesLiveData = MutableLiveData<NoteResult>()
    private val lastRetrieveNoteLiveData = MutableLiveData<NoteResult>()
    private val lastSaveNoteLiveData = MutableLiveData<NoteResult>()

    override fun subscribeToAllNotes(): LiveData<NoteResult> {
        notesReference.addSnapshotListener { snapshot, err ->
            snapshot?.run {
                documents.map { doc -> doc.toObject(Note::class.java) }
                    .let { listNotesLiveData.value = NoteResult.Success(it) }
            } ?: err?.let { listNotesLiveData.value = NoteResult.Error(it) }
        }
        return listNotesLiveData
    }

    override fun getNoteById(id: String): LiveData<NoteResult> {
        notesReference.document(id).get()
            .addOnSuccessListener {
                lastRetrieveNoteLiveData.value = NoteResult.Success(it.toObject(Note::class.java))
            }.addOnFailureListener {
                lastRetrieveNoteLiveData.value = NoteResult.Error(it)
            }
        return lastRetrieveNoteLiveData
    }

    override fun saveNote(note: Note): LiveData<NoteResult> {
        notesReference.document(note.id).set(note)
            .addOnSuccessListener {
                lastSaveNoteLiveData.value = NoteResult.Success(it)
            }.addOnFailureListener {
                lastSaveNoteLiveData.value = NoteResult.Error(it)
            }
        return lastSaveNoteLiveData
    }
}