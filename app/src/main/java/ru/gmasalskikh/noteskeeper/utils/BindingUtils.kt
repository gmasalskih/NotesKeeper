package ru.gmasalskikh.noteskeeper.utils

import android.widget.TextView
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import ru.gmasalskikh.noteskeeper.data.entity.Note
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("BA_cardView_set_background_color")
fun CardView.setNoteBackgroundColor(item: Note?) {
    item?.let { note: Note ->
        setBackgroundColor(ResourcesCompat.getColor(resources, note.color, null))
    }
}

@BindingAdapter("BA_textView_set_title")
fun TextView.setNoteTitle(item: Note?) {
    item?.let { note: Note ->
        text = note.title
    }
}

@BindingAdapter("BA_textView_set_text")
fun TextView.setNoteText(item: Note?) {
    item?.let { note: Note ->
        text = note.text
    }
}

@BindingAdapter("BA_toolbar_set_background_color")
fun Toolbar.setToolBarBackgroundColor(item: Note?) {
    item?.let { note: Note ->
        setBackgroundColor(ResourcesCompat.getColor(resources, note.color, null))
    }
}

@BindingAdapter("BA_toolbar_set_title")
fun Toolbar.setToolbarTitle(item: Note?) {
    item?.let { note: Note ->
        title = SimpleDateFormat("dd.MM.yy HH:mm", Locale.getDefault()).format(note.lastChanged)
    }
}