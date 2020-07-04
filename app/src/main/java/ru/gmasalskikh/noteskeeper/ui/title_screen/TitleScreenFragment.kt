package ru.gmasalskikh.noteskeeper.ui.title_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.gmasalskikh.noteskeeper.databinding.TitleScreenFragmentBinding

class TitleScreenFragment : Fragment() {

    private lateinit var binding: TitleScreenFragmentBinding
    private val viewModel: TitleScreenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TitleScreenFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

}