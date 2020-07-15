package ru.gmasalskikh.noteskeeper.ui.list_notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.databinding.ListNotesFragmentBinding
import ru.gmasalskikh.noteskeeper.ui.BaseFragment

class ListNotesFragment : BaseFragment<List<Note>?, ListNotesViewState>() {

    private lateinit var binding: ListNotesFragmentBinding
    private lateinit var adapter: ListNotesAdapter
    override val viewModel: ListNotesViewModel by viewModel()
    override lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListNotesFragmentBinding.inflate(inflater, container, false)
        adapter = ListNotesAdapter(
            ListNotesAdapter.NoteClickListener { note ->
                viewModel.onClickNote(note)
            }
        )
        toolbar = binding.toolbar
        binding.lifecycleOwner = this
        binding.rvNotes.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvNotes.adapter = adapter
        return binding.root
    }

    override fun initState() {
        super.initState()
        binding.fab.setOnClickListener { navigateToNoteDetails(null) }
        viewModel.selectNote.observe(viewLifecycleOwner, Observer { note ->
            if (note != null) navigateToNoteDetails(note.id)
        })
    }

    override fun renderData(data: List<Note>?) {
        adapter.submitList(data)
    }

    override fun renderErr(err: Throwable) {
        adapter.submitList(null)
        Toast.makeText(requireContext(), err.message, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToNoteDetails(id: String?) {
        val action = ListNotesFragmentDirections.actionListNotesFragmentToNoteDetailsFragment(id)
        navController.navigate(action)
    }
}