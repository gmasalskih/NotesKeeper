package ru.gmasalskikh.noteskeeper.data

import org.koin.java.KoinJavaComponent.inject

object ColorProvider {
    private val colorRepository: ColorRepository by inject(ColorRepository::class.java)
    fun getRandomColor(): Int = colorRepository.getRandomColor()
}