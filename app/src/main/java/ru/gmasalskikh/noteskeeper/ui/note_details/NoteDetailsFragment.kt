package ru.gmasalskikh.noteskeeper.ui.note_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.databinding.NoteDetailsFragmentBinding
import ru.gmasalskikh.noteskeeper.ui.BaseFragment
import ru.gmasalskikh.noteskeeper.utils.format

class NoteDetailsFragment : BaseFragment<Note?, NoteDetailsViewState>() {

    private lateinit var binding: NoteDetailsFragmentBinding
    private lateinit var args: NoteDetailsFragmentArgs
    override val viewModel: NoteDetailsViewModel by viewModel { parametersOf(args.id) }
    override lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NoteDetailsFragmentBinding.inflate(inflater, container, false)
        args = NoteDetailsFragmentArgs.fromBundle(requireArguments())
        binding.lifecycleOwner = this
        toolbar = binding.toolbar
        return binding.root
    }

    override fun initState() {
        super.initState()
        binding.noteTitle.addTextChangedListener { viewModel.onTextChangeTitle(it.toString()) }
        binding.noteText.addTextChangedListener { viewModel.onTextChangeText(it.toString()) }
    }

    override fun renderData(data: Note?) {
        if (data == null) return
        binding.noteTitle.setText(data.title)
        binding.noteText.setText(data.text)
        binding.toolbar.setBackgroundColor(data.color)
        binding.toolbar.title = data.lastChanged.format()
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveChanges()
    }

    override fun renderErr(err: Throwable) {
        Toast.makeText(requireContext(), err.message, Toast.LENGTH_SHORT).show()
    }
}
