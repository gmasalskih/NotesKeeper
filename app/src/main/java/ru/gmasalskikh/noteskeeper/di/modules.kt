package ru.gmasalskikh.noteskeeper.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gmasalskikh.noteskeeper.ui.list_notes.ListNotesViewModel


val titleModule = module {
    viewModel { ListNotesViewModel() }
}