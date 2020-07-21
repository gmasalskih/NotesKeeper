package ru.gmasalskikh.noteskeeper.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(): String = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault()).format(this)