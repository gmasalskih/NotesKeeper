package ru.gmasalskikh.noteskeeper.di

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.model.FirestoreProvider
import ru.gmasalskikh.noteskeeper.ui.list_notes.ListNotesViewModel
import ru.gmasalskikh.noteskeeper.ui.note_details.NoteDetailsViewModel

val providersModule = module {
    single { FirebaseFirestore.getInstance() }
    single<INotesProvider> { FirestoreProvider(get()) }
    single { NotesRepository(get()) }
}

val listNotesModule = module {
    viewModel { ListNotesViewModel(notesRepository = get()) }
}

val noteDetailsModule = module {
    viewModel { (id: String?) -> NoteDetailsViewModel(notesRepository = get(), id = id) }
}
