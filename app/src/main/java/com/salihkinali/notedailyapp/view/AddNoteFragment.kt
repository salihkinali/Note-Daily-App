package com.salihkinali.notedailyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databese.NoteDatabese
import com.salihkinali.notedailyapp.databinding.FragmentAddNoteBinding
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModelFactory


class AddNoteFragment : Fragment() {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: NoteDatabese
    private var selectedNoteColor: String = "282829"
    private lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = NoteDatabese.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater,container,false)

        val application = requireNotNull(this.activity).application

        val dbDao = NoteDatabese.getInstance(application)?.noteDatabeseDao

        val viewModelFactory = dbDao?.let { NoteViewModelFactory(it,application) }

        viewModel = viewModelFactory?.let {

            ViewModelProvider(this,it)[NoteViewModel::class.java]
        }!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
          viewColor1.setOnClickListener{
              selectedNoteColor = "282829"
              imageColor1.setImageResource(R.drawable.ic_check)
              imageColor2.setImageResource(0)
              imageColor3.setImageResource(0)
              imageColor4.setImageResource(0)
              imageColor5.setImageResource(0)

          }
            viewColor2.setOnClickListener {
                selectedNoteColor = "05595B"
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(R.drawable.ic_check)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor3.setOnClickListener {
                selectedNoteColor = "F6F54D"
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(R.drawable.ic_check)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor4.setOnClickListener {
                selectedNoteColor = "344CB7"
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(R.drawable.ic_check)
                imageColor5.setImageResource(0)
            }
            viewColor5.setOnClickListener {
                selectedNoteColor = "F55353"
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(R.drawable.ic_check)
            }
            addButton.setOnClickListener {
                val title = noteTitle.text.toString()
                val categoryText = category.text.toString()
                val inside = note.text.toString()
                if(title.isNotEmpty() && categoryText.isNotEmpty() && inside.isNotEmpty()){

                    viewModel.addNote(
                        NoteModel(
                            noteTitle = title,
                            noteCategory = categoryText,
                            noteInside = inside,
                            noteColor = selectedNoteColor
                        )
                    )
                    val action = AddNoteFragmentDirections.addNoteToHomeFragment()
                    findNavController().navigate(action)
                }else{
                    Toast.makeText(context,"Please fill in the blanks",Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}