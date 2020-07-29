package ru.gmasalskikh.noteskeeper.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseUser
import ru.gmasalskikh.noteskeeper.R
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

fun <T> LiveData<T>.observeOnce(callable: (data: T) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T) {
            callable(t)
            removeObserver(this)
        }
    })
}

fun String.toToast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun ImageView.circleImgFromUrl(context: Context, url:String){
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.android_robot)
        .error(R.drawable.android_robot)
        .circleCrop()
        .into(this)
}