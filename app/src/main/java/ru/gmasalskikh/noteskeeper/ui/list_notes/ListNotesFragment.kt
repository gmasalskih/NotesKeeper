package ru.gmasalskikh.noteskeeper.ui.list_notes

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.databinding.ListNotesFragmentBinding
import timber.log.Timber

class ListNotesFragment : Fragment() {

    private lateinit var binding: ListNotesFragmentBinding
    private lateinit var adapter: ListNotesAdapter
    private lateinit var navController: NavController
    private val viewModel: ListNotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListNotesFragmentBinding.inflate(inflater, container, false)
        adapter = ListNotesAdapter(
            ListNotesAdapter.NoteClickListener { note: Note ->
                viewModel.onClickNote(note)
            }
        )
        binding.lifecycleOwner = this
        binding.rvNotes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvNotes.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
        navController = view.findNavController()
        val appBarConfiguration= AppBarConfiguration(navController.graph, drawerLayout = requireActivity().drawerLayout)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        initObserveViewModel()
    }

    private fun initObserveViewModel() {
        binding.fab.setOnClickListener {
            val action = ListNotesFragmentDirections.actionListNotesFragmentToNoteDetailsFragment(null)
            navController.navigate(action)
        }
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.data?.let {
                adapter.submitList(it)
            }
        })
        viewModel.selectNote.observe(viewLifecycleOwner, Observer { note: Note? ->
            if (note != null){
                Timber.i("--- $note")
                val action = ListNotesFragmentDirections.actionListNotesFragmentToNoteDetailsFragment(note.id)
                navController.navigate(action)
            }
        })
    }
}