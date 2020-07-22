package ru.gmasalskikh.noteskeeper.utils

import com.google.firebase.auth.FirebaseUser
import ru.gmasalskikh.noteskeeper.data.entity.User
import java.text.SimpleDateFormat
import java.util.*

fun Date.format(): String = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault()).format(this)
fun FirebaseUser.toUser() = User(
    id = this.uid,
    name = this.displayName ?: "",
    email = this.email ?: "",
    avatarUri = this.photoUrl?.toString() ?: ""
)
