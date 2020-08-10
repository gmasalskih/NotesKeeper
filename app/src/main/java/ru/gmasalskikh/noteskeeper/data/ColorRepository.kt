package ru.gmasalskikh.noteskeeper.data

import android.content.Context
import androidx.core.content.ContextCompat
import ru.gmasalskikh.noteskeeper.R

class ColorRepository(private val context: Context) {

    companion object{
        val listOfColors = listOf(
            R.color.yellow,
            R.color.green,
            R.color.blue,
            R.color.red,
            R.color.violet
        )
    }

    fun getRandomColor(): Int = ContextCompat.getColor(context, listOfColors.random())
}