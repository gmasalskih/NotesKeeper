package ru.gmasalskikh.noteskeeper.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.nav_header.view.*
import ru.gmasalskikh.noteskeeper.R
import ru.gmasalskikh.noteskeeper.data.entity.User
import ru.gmasalskikh.noteskeeper.databinding.ActivityMainBinding
import ru.gmasalskikh.noteskeeper.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.gmasalskikh.noteskeeper.ui.splash.SplashActivity
import ru.gmasalskikh.noteskeeper.utils.circleImgFromUrl

class MainActivity : BaseActivity<User?, MainActivityViewSate>() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navView: View
    override val viewModel: MainActivityViewModel by viewModel()

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
        viewModel.initViewState()
        binding.navMenu.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.logOut -> {
                    viewModel.signOut()
                    true
                }
                else -> false
            }
        }
    }

    private fun navigateToSplashActivity() { startActivity(SplashActivity.getIntent(this)) }

    override fun onResume() {
        super.onResume()
        super.initViewState()
    }

    override fun renderData(data: User?) {
        data?.let {
            navView.user_name.text = it.name
            navView.user_email.text = it.email
            navView.avatar.circleImgFromUrl(this, it.avatarUri)
        } ?: navigateToSplashActivity()
    }

    override fun renderErr(err: Throwable) { navigateToSplashActivity() }
}