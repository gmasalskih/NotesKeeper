package ru.gmasalskikh.noteskeeper.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gmasalskikh.noteskeeper.data.LocalNotesRepository
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.ui.list_notes.ListNotesViewModel
import ru.gmasalskikh.noteskeeper.ui.note_details.NoteDetailsViewModel

val providersModule = module {
    single<INotesProvider> { LocalNotesRepository }
}

val listNotesModule = module {
    viewModel { ListNotesViewModel(get()) }
}

val noteDetailsModule = module {
    viewModel { (id: String?) ->
        NoteDetailsViewModel(
            notesRepository = get(),
            id = id
        )
    }
}
