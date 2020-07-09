package ru.gmasalskikh.noteskeeper.utils

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import ru.gmasalskikh.noteskeeper.data.entity.Note

@BindingAdapter("noteBackgroundColor")
fun CardView.setBackgroundColor(item: Note?) {
    item?.let { note: Note ->
        setBackgroundColor(note.color)
    }
}

@BindingAdapter("noteTitle")
fun TextView.setTitle(item: Note?) {
    item?.let { note: Note ->
        text = note.title
    }
}

@BindingAdapter("noteText")
fun TextView.setName(item: Note?) {
    item?.let { note: Note ->
        text = note.text
    }
}