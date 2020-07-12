package ru.gmasalskikh.noteskeeper.ui.note_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.gmasalskikh.noteskeeper.databinding.NoteDetailsFragmentBinding
import timber.log.Timber

class NoteDetailsFragment : Fragment() {

    private lateinit var binding: NoteDetailsFragmentBinding
    private lateinit var navController: NavController
    private lateinit var args: NoteDetailsFragmentArgs
    private val viewModel:NoteDetailsViewModel by viewModel { parametersOf(args.id) }

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
        initObserveViewModel()
        requireActivity().toolbar.title = "sadsadasd"
    }

    private fun initObserveViewModel(){
        viewModel.backgroundColor.observe(viewLifecycleOwner, Observer {
            Timber.i("--- $it")
            requireActivity().toolbar.setBackgroundColor(ResourcesCompat.getColor(resources, it, null))
        })
    }
}