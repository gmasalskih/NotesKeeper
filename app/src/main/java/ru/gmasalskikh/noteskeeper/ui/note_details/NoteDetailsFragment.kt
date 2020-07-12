package ru.gmasalskikh.noteskeeper.ui.note_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.gmasalskikh.noteskeeper.databinding.NoteDetailsFragmentBinding

class NoteDetailsFragment : Fragment() {

    private lateinit var binding: NoteDetailsFragmentBinding
    private lateinit var navController: NavController
    private lateinit var args: NoteDetailsFragmentArgs
    private val viewModel: NoteDetailsViewModel by viewModel { parametersOf(args.id) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NoteDetailsFragmentBinding.inflate(inflater, container, false)
        args = NoteDetailsFragmentArgs.fromBundle(requireArguments())
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        val appBarConfiguration =
            AppBarConfiguration(navController.graph, drawerLayout = requireActivity().drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}

