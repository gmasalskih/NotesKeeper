package ru.gmasalskikh.noteskeeper.utils

import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import ru.gmasalskikh.noteskeeper.data.entity.Note

@BindingAdapter("noteBackgroundColor")
fun CardView.setBackgroundColor(item: Note?) {
    item?.let { note: Note ->
        setBackgroundColor(ResourcesCompat.getColor(resources, note.color, null))
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