package com.salihkinali.notedailyapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.adapter.NoteAdapter
import com.salihkinali.notedailyapp.databese.NoteDatabese
import com.salihkinali.notedailyapp.databinding.FragmentHomeBinding
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModelFactory

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteList: List<NoteModel>
    private lateinit var db: NoteDatabese
    private lateinit var viewModel: NoteViewModel
    private val adapter by lazy { NoteAdapter(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = NoteDatabese.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dbDao = NoteDatabese.getInstance(application)?.noteDatabeseDao

        val viewModelFactory = dbDao?.let { NoteViewModelFactory(it, application) }

        viewModel = viewModelFactory?.let {

            ViewModelProvider(this, it)[NoteViewModel::class.java]
        }!!

        viewModel.noteList.observe(viewLifecycleOwner, Observer { noteLists ->
            noteList = noteLists
            adapter.updateList(noteList)
            binding.noteReyclerView.adapter = adapter

        })


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllNote()
        adapter.onTodoClick = {notePosition,rvPosition ->
            val popup = PopupMenu(requireContext(),binding.noteReyclerView[rvPosition].findViewById(
                R.id.popup))
            popup.inflate(R.menu.popup_menu)
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.sil -> {viewModel.deleteNote(notePosition)
                    true
                    }
                    R.id.duzenle -> {
                        val action = HomeFragmentDirections.actionHomeToDetailNote(notePosition)
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
        binding.addNoteButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddNote()
            findNavController().navigate(action)
        }

    }

    private fun getAllNote() {
        viewModel.noteList.observe(viewLifecycleOwner, Observer { noteLists ->
            noteList = noteLists
            binding.apply {
                if (noteList.isEmpty()) {
                    noteReyclerView.visibility = View.GONE
                    animationView.visibility = View.VISIBLE
                    animationTextView.visibility = View.VISIBLE
                } else {
                    noteReyclerView.visibility = View.VISIBLE
                    animationView.visibility = View.GONE
                    animationTextView.visibility = View.GONE
                    noteReyclerView.layoutManager =
                        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                    noteReyclerView.setHasFixedSize(true)

                }

            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}