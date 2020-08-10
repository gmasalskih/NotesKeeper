package ru.gmasalskikh.noteskeeper.di

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gmasalskikh.noteskeeper.data.ColorRepository
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.model.FirestoreProvider
import ru.gmasalskikh.noteskeeper.ui.list_notes.ListNotesViewModel
import ru.gmasalskikh.noteskeeper.ui.main.MainActivityViewModel
import ru.gmasalskikh.noteskeeper.ui.note_details.NoteDetailsViewModel
import ru.gmasalskikh.noteskeeper.ui.splash.SplashActivityViewModel

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
val providersModule = module {
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single {
        FirestoreProvider(
            store = get(),
            auth = get(),
            authUI = get(),
            context = androidContext()
        )
    }
    single <INotesProvider> { NotesRepository(get()) }
    single { ColorRepository(androidContext()) }
    single { AuthUI.getInstance() }
    factory { (drawable: Int, style: Int) ->
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLogo(drawable)
            .setTheme(style)
            .setAvailableProviders(listOf(AuthUI.IdpConfig.GoogleBuilder().build()))
            .build()
    }
}

val mainModule = module {
    viewModel { MainActivityViewModel(notesRepository = get()) }
}

val splashModule = module {
    viewModel { SplashActivityViewModel(notesRepository = get()) }
}

val listNotesModule = module {
    viewModel { ListNotesViewModel(notesRepository = get()) }
}

val noteDetailsModule = module {
    viewModel { (id: String?) -> NoteDetailsViewModel(notesRepository = get(), id = id) }
}