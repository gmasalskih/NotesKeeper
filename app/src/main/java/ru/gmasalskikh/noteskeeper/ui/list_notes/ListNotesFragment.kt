package ru.gmasalskikh.noteskeeper.ui.list_notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.gmasalskikh.noteskeeper.R
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.databinding.ListNotesFragmentBinding
import ru.gmasalskikh.noteskeeper.ui.BaseFragment
import ru.gmasalskikh.noteskeeper.utils.toToast

class ListNotesFragment : BaseFragment<List<Note>?, ListNotesViewState>() {

    private lateinit var binding: ListNotesFragmentBinding
    private lateinit var adapter: ListNotesAdapter
    override lateinit var toolbar: Toolbar
    override val viewModel: ListNotesViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListNotesFragmentBinding.inflate(inflater, container, false)
        adapter = ListNotesAdapter(
            clickListener = ListNotesAdapter.NoteClickListener { note -> viewModel.onClickNote(note) },
            longClickListener = ListNotesAdapter.NoteLongClickListener { note ->
                viewModel.onLongClickNote(note)
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
        viewModel.delNote.observe(viewLifecycleOwner, Observer { note ->
            note?.let {createAlertDialog(note).show() }
        })
        viewModel.delNoteMsg.observe(viewLifecycleOwner, Observer {
            "$it ${getText(R.string.note_was_deleted)}".toToast(requireContext())
        })
        viewModel.delNoteErr.observe(viewLifecycleOwner, Observer {
            it.toToast(requireContext())
        })
    }

    private fun createAlertDialog(note: Note) = AlertDialog.Builder(requireActivity())
        .setCancelable(true)
        .setTitle("${getText(R.string.del_note_alert_title)} ${note.title}?")
        .setMessage(getText(R.string.del_note_alert_text))
        .setPositiveButton(getText(R.string.yes)) { _, _ ->
            viewModel.delNote(note)
        }.setNegativeButton(getText(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }.create()

    override fun renderData(data: List<Note>?) = adapter.submitList(data)

    override fun renderErr(err: Throwable) {
        adapter.submitList(null)
        err.message?.toToast(requireContext())
    }

    private fun navigateToNoteDetails(id: String?) {
        val action = ListNotesFragmentDirections.actionListNotesFragmentToNoteDetailsFragment(id)
        navController.navigate(action)
    }
}