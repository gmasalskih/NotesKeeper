package ru.gmasalskikh.noteskeeper.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseFragment<T, S : BaseViewState<T>> : Fragment() {

    abstract val viewModel: BaseViewModel<T, S>
    protected lateinit var navController: NavController
    abstract val toolbar: Toolbar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        val appBarConfiguration =
            AppBarConfiguration(navController.graph, drawerLayout = requireActivity().drawerLayout)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        initState()
    }

    open fun initState() {
        viewModel.getViewState().observe(viewLifecycleOwner, Observer { state ->
            state.data?.let { data -> renderData(data) } ?: state.err?.let { err -> renderErr(err) }
        })
    }

    abstract fun renderData(data: T)
    abstract fun renderErr(err: Throwable)
}