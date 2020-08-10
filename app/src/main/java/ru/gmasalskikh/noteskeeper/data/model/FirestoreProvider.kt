package ru.gmasalskikh.noteskeeper.data.model

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.tasks.await
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.utils.toUser
import java.lang.Exception

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class FirestoreProvider(
    private val store: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val authUI: AuthUI,
    private val context: Context
) {

    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    suspend fun deleteNote(id: String) = try {
        Result.Success(getUserNotesCollectionReference().document(id).delete().await())
    } catch (e: Exception) {
        Result.Error(e)
    }

    suspend fun saveNote(note: Note) = try {
        Result.Success(getUserNotesCollectionReference().document(note.id).set(note).await())
    } catch (e: Exception) {
        Result.Error(e)
    }

    suspend fun getNoteById(id: String) = try {
        Result.Success(
            getUserNotesCollectionReference().document(id).get().await().toObject<Note>()
        )
    } catch (e: Exception) {
        Result.Error(e)
    }

    suspend fun singOut() = try {
        Result.Success(authUI.signOut(context).await())
    } catch (e: Exception) {
        Result.Error(e)
    }

    fun getCurrentUser() = auth.currentUser?.toUser() ?: throw UnauthorizedUserException()

    private fun getUserNotesCollectionReference() =
        store.collection(USERS_COLLECTION).document(getCurrentUser().id).collection(NOTES_COLLECTION)

    suspend fun getUserNotes(): ReceiveChannel<Result> {
        val bc = BroadcastChannel<Result>(1)
        val scope = CoroutineScope(Dispatchers.IO)
        try {
            getUserNotesCollectionReference().addSnapshotListener { snapshot, error ->
                snapshot?.run {
                    documents.asSequence()
                        .map { doc -> doc.toObject<Note>() }
                        .filterNotNull()
                        .sorted()
                        .toList()
                        .reversed()
                        .let { listNotes -> scope.launch { bc.send(Result.Success(listNotes)) } }
                } ?: error?.let { scope.launch { bc.send(Result.Error(it)) } }
            }
        } catch (e: Exception) {
            scope.launch { bc.send(Result.Error(e)) }
        } finally {
            return bc.openSubscription()
        }
    }
}

class UnauthorizedUserException : Exception()