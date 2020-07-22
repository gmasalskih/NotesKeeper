package ru.gmasalskikh.noteskeeper.di

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gmasalskikh.noteskeeper.data.ColorRepository
import ru.gmasalskikh.noteskeeper.data.INotesProvider
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.model.FirestoreProvider
import ru.gmasalskikh.noteskeeper.ui.list_notes.ListNotesViewModel
import ru.gmasalskikh.noteskeeper.ui.main.MainActivityPresenter
import ru.gmasalskikh.noteskeeper.ui.main.MainPresenter
import ru.gmasalskikh.noteskeeper.ui.main.MainView
import ru.gmasalskikh.noteskeeper.ui.note_details.NoteDetailsViewModel
import ru.gmasalskikh.noteskeeper.ui.splash.SplashActivityPresenter
import ru.gmasalskikh.noteskeeper.ui.splash.SplashPresenter
import ru.gmasalskikh.noteskeeper.ui.splash.SplashView

val providersModule = module {
    single { FirebaseFirestore.getInstance() }
    single { FirebaseAuth.getInstance() }
    single<INotesProvider> { FirestoreProvider(get(), get()) }
    single { NotesRepository(get()) }
    single { ColorRepository(androidContext()) }
    single { AuthUI.getInstance() }
}

val presentersModule = module {
    factory<MainPresenter> { (activity: MainView) ->
        MainActivityPresenter(
            activity = activity,
            notesRepository = get(),
            authUI = get()
        )
    }

    factory<SplashPresenter> { (activity: SplashView) ->
        SplashActivityPresenter(
            activity = activity,
            authUI = get(),
            auth = get()
        )
    }
}

val listNotesModule = module {
    viewModel { ListNotesViewModel(notesRepository = get()) }
}

val noteDetailsModule = module {
    viewModel { (id: String?) -> NoteDetailsViewModel(notesRepository = get(), id = id) }
}