package ru.gmasalskikh.noteskeeper.ui.note_details

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.note_details_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.gmasalskikh.noteskeeper.R
import ru.gmasalskikh.noteskeeper.data.entity.Note
import ru.gmasalskikh.noteskeeper.databinding.NoteDetailsFragmentBinding
import ru.gmasalskikh.noteskeeper.ui.BaseFragment
import ru.gmasalskikh.noteskeeper.utils.format
import timber.log.Timber

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
        setHasOptionsMenu(true)
        args = NoteDetailsFragmentArgs.fromBundle(requireArguments())
        binding.lifecycleOwner = this
        toolbar = binding.toolbar
        toolbar.inflateMenu(R.menu.note)
        toolbar.setOnMenuItemClickListener {

            when(it.itemId){
                android.R.id.home -> requireActivity().onBackPressed().let { true }
                R.id.palette -> togglePalette().let { true }
                R.id.delete -> deleteNote().let { true }
                else -> false
            }
        }
        binding.colorPicker.onColorClickListener = {
            Timber.i("--- onColorClickListener $it")
        }
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

    private fun togglePalette() {
        if(binding.colorPicker.isOpen){
            binding.colorPicker.close()
        } else {
            binding.colorPicker.open()
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.del_note_alert_text))
            .setNegativeButton(getString(R.string.no)) { dialog, which -> dialog.dismiss() }
            .setPositiveButton(getString(R.string.yes)) { dialog, which ->
                Timber.i("--- deleteNote")
            }
            .show()
    }
}
