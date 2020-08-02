package ru.gmasalskikh.noteskeeper

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.gmasalskikh.noteskeeper.di.*
import timber.log.Timber

class NotesKeeperApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@NotesKeeperApp)
            modules(
                providersModule,
                mainModule,
                splashModule,
                listNotesModule,
                noteDetailsModule
            )
        }
    }
}
