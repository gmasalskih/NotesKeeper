package ru.gmasalskikh.noteskeeper.ui

abstract class BaseViewState<T>(open val data: T, open val err: Throwable?)