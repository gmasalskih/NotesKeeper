package ru.gmasalskikh.noteskeeper.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, S>

    abstract fun renderData(data: T)
    abstract fun renderErr(err: Throwable)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) initViewState()
    }

    protected fun initViewState() {
        viewModel.getViewState().observe(this, Observer { state ->
            state.data?.let { data -> renderData(data) } ?: state.err?.let { err -> renderErr(err) }
        })
    }
}