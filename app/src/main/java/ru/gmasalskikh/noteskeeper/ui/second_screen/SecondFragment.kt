package ru.gmasalskikh.noteskeeper.ui.second_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.gmasalskikh.noteskeeper.databinding.SecondFragmentBinding

class SecondFragment : Fragment() {

    private val viewModel: SecondViewModel by viewModel()
    private lateinit var binding: SecondFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SecondFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

}