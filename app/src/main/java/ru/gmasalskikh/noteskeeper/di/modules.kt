package ru.gmasalskikh.noteskeeper.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gmasalskikh.noteskeeper.ui.second_screen.SecondViewModel
import ru.gmasalskikh.noteskeeper.ui.title_screen.TitleScreenViewModel

val titleModule = module {
    viewModel { TitleScreenViewModel() }
}

val secondModule = module {
    viewModel { SecondViewModel() }
}