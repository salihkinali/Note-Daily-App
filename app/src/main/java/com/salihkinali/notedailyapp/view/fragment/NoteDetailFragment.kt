package com.salihkinali.notedailyapp.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databese.NoteDatabese
import com.salihkinali.notedailyapp.databinding.FragmentNoteDetailBinding
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModelFactory

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private val args: NoteDetailFragmentArgs by navArgs()
    private lateinit var notes: NoteModel
    private lateinit var db: NoteDatabese
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = NoteDatabese.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        notes = args.note
        val application = requireNotNull(this.activity).application

        val dbDao = NoteDatabese.getInstance(application)?.noteDatabeseDao

        val viewModelFactory = dbDao?.let { NoteViewModelFactory(it, application) }

        viewModel = viewModelFactory?.let {

            ViewModelProvider(this, it)[NoteViewModel::class.java]
        }!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            noteTitle.setText(notes.noteTitle)
            category.setText(notes.noteCategory)
             note.setText(notes.noteInside)

            updateButton.setOnClickListener {

                    notes.noteTitle = noteTitle.text.toString()
                    notes.noteCategory = category.text.toString()
                    notes.noteInside = note.text.toString()

                    viewModel.updateNote(notes)
                    val action = NoteDetailFragmentDirections.actionDetailToHomeFragment()
                    findNavController().navigate(action)
            }
            deleteButton.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(R.string.dialogTitle)
                builder.setMessage(R.string.dialogMessage)
                builder.setIcon(android.R.drawable.ic_dialog_alert)

                builder.setPositiveButton("Yes"){dialogInterface, which ->
                    viewModel.deleteNote(notes)
                    val action = NoteDetailFragmentDirections.actionDetailToHomeFragment()
                    findNavController().navigate(action)
                    Toast.makeText(context,"Note Deleted",Toast.LENGTH_LONG).show()
                }

                builder.setNegativeButton("No"){dialogInterface, which ->

                }
                val alertDialog: AlertDialog = builder.create()
                // Set other dialog properties
                alertDialog.setCancelable(false)
                alertDialog.show()


            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}