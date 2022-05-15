package com.salihkinali.notedailyapp.view.fragment

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databese.NoteDatabese
import com.salihkinali.notedailyapp.databinding.FragmentNoteDetailBinding
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModelFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private val args: NoteDetailFragmentArgs by navArgs()
    private var selectedNoteColor: String = "#282829"
    private var selectedRadioState: String = "Eğitim"
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            noteTitle.setText(notes.noteTitle)
            when (notes.noteCategory) {
                "Eğitim" -> egitim.isChecked = true
                "Yaşam" -> yasam.isChecked = true
                "Eğlence" -> eglence.isChecked = true
                else -> diger.isChecked = true
            }
            when (notes.noteColor) {

                "#282829" -> imageColor1.setImageResource(R.drawable.ic_check)
                "#007C3F" -> imageColor2.setImageResource(R.drawable.ic_check)
                "#F6F54D" -> imageColor3.setImageResource(R.drawable.ic_check)
                "#344CB7" -> imageColor4.setImageResource(R.drawable.ic_check)
                else -> imageColor5.setImageResource(R.drawable.ic_check)
            }
            note.setText(notes.noteInside)
            viewColor1.setOnClickListener {
                selectedNoteColor = "#282829"
                imageColor1.setImageResource(R.drawable.ic_check)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor2.setOnClickListener {
                selectedNoteColor = "#007C3F"
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(R.drawable.ic_check)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor3.setOnClickListener {
                selectedNoteColor = "#F6F54D"
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(R.drawable.ic_check)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor4.setOnClickListener {
                selectedNoteColor = "#344CB7"
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(R.drawable.ic_check)
                imageColor5.setImageResource(0)
            }
            viewColor5.setOnClickListener {
                selectedNoteColor = "#F55353"
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(R.drawable.ic_check)
            }
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.egitim -> selectedRadioState = "Eğitim"
                    R.id.yasam -> selectedRadioState = "Yaşam"
                    R.id.eglence -> selectedRadioState = "Eğlence"
                    else -> selectedRadioState = "Diğer"
                }
            }
            val current = LocalDateTime.now()
            val formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
            val formattedDate = current.format(formatterDate)
            val formattedTime = current.format(formatterTime)
            updateButton.setOnClickListener {

                val title = noteTitle.text.toString()
                val inside = note.text.toString()

                if(title.isNotEmpty() && inside.isNotEmpty()){

                    notes.noteTitle = title
                    notes.noteCategory = selectedRadioState
                    notes.noteColor = selectedNoteColor
                    notes.noteInside = inside
                    notes.dateTime = formattedDate
                    notes.timeNow = formattedTime
                    viewModel.updateNote(notes)
                    val action = NoteDetailFragmentDirections.actionDetailToHomeFragment()
                    findNavController().navigate(action)

                }else{
                    Snackbar.make(it,"Lütfen Boş Alan Bırakmayın.",Snackbar.LENGTH_LONG).show()
                }

            }


        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}