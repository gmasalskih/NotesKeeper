package ru.gmasalskikh.noteskeeper.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.gmasalskikh.noteskeeper.R
import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.databinding.ActivityMainBinding
import ru.gmasalskikh.noteskeeper.ui.BaseViewState
import ru.gmasalskikh.noteskeeper.ui.splash.SplashActivity

class MainActivity : AppCompatActivity(), MainView {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val presenter: MainPresenter by inject { parametersOf(this) }
    private lateinit var navView: View
    private lateinit var userAvatar: ImageView
    private lateinit var userName: TextView
    private lateinit var userEmail: TextView

    companion object {
        fun getIntent(context: Context) = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navController = findNavController(R.id.navHostFragment)
        binding.navMenu.setupWithNavController(navController)
        navView = binding.navMenu.getHeaderView(0)
        userAvatar = navView.findViewById(R.id.avatar)
        userName = navView.findViewById(R.id.user_name)
        userEmail = navView.findViewById(R.id.user_email)
        presenter.initViewState()
        binding.navMenu.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.logOut -> {
                    presenter.logOut()
                    true
                }
                else -> false
            }
        }
    }

    override fun onPause() {
        presenter.onCleared()
        super.onPause()

    }

    override fun renderViewState(viewState: BaseViewState<User?>) {
        viewState.data?.let { user ->
            userName.text = user.name
            userEmail.text = user.email
            Glide.with(this)
                .load(user.avatarUri)
                .placeholder(R.drawable.android_robot)
                .error(R.drawable.android_robot)
                .circleCrop()
                .into(userAvatar)
        }
    }

    override fun navigateToSplashActivity() {
        startActivity(SplashActivity.getIntent(this))
    }

}