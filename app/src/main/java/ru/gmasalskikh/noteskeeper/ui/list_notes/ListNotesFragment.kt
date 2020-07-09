package ru.gmasalskikh.noteskeeper.ui.list_notes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.gmasalskikh.noteskeeper.data.NotesRepository
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.databinding.ListNotesFragmentBinding

class ListNotesFragment : Fragment() {

    private lateinit var binding: ListNotesFragmentBinding
    private lateinit var adapter: NotesListAdapter
    private lateinit var navController: NavController
    private val viewModel: ListNotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListNotesFragmentBinding.inflate(inflater, container, false)
        adapter = NotesListAdapter(
            NotesListAdapter.NoteClickListener { note: Note ->
                viewModel.onClickNote(note)
            }
        )
        adapter.submitList(NotesRepository.getNotes())
        binding.lifecycleOwner = this
        binding.rvNotes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvNotes.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
        initObserveViewModel()
    }

    private fun initObserveViewModel() {
        viewModel.selectNote.observe(viewLifecycleOwner, Observer { note: Note ->
            Toast.makeText(requireContext(), note.toString(), Toast.LENGTH_LONG).show()
        })
    }
}