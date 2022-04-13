package com.salihkinali.notedailyapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.GeneratedAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.salihkinali.notedailyapp.adapter.NoteAdapter
import com.salihkinali.notedailyapp.databese.NoteDatabese
import com.salihkinali.notedailyapp.databinding.FragmentHomeBinding
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModelFactory

class HomeFragment : Fragment() {
   private var _binding:FragmentHomeBinding? = null
   private val binding get() = _binding!!
    private lateinit var noteList: List<NoteModel>
    private lateinit var db: NoteDatabese
    private lateinit var viewModel: NoteViewModel
    private lateinit var adapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      db = NoteDatabese.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater,container,false)

        val application = requireNotNull(this.activity).application

        val dbDao = NoteDatabese.getInstance(application)?.noteDatabeseDao

        val viewModelFactory = dbDao?.let { NoteViewModelFactory(it,application) }

        viewModel = viewModelFactory?.let {

            ViewModelProvider(this,it)[NoteViewModel::class.java]
        }!!

        viewModel.noteList.observe(viewLifecycleOwner, Observer {noteLists->
            noteList = noteLists
            adapter = NoteAdapter(noteList)
            binding.noteReyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllNote()
        binding.addNoteButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddNote()
            findNavController().navigate(action)
        }

    }

    private fun getAllNote() {
        viewModel.noteList.observe(viewLifecycleOwner, Observer {noteLists->
            noteList = noteLists
            binding.apply {
                if (noteList.isEmpty()) {
                    Snackbar.make(requireView(), "?r?n bulunamad?", 1000).show()
                } else {
                noteReyclerView.layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
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