package com.salihkinali.notedailyapp.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
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

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteList: List<NoteModel>
    private lateinit var db: NoteDatabese
    private lateinit var viewModel: NoteViewModel
    private val adapter by lazy { NoteAdapter(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        db = NoteDatabese.getInstance(requireContext())!!
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.noteReyclerView.layoutManager =
            LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding.noteReyclerView.adapter = adapter
        getAllNote()
        adapter.onTodoClick = { notePosition, rvPosition ->
          viewModel.addNote(notePosition)
        }
        binding.addNoteButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddNote()
            findNavController().navigate(action)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
        val search = menu.findItem(R.id.item_search)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter -> {
                Toast.makeText(requireContext(), "Filtreye Tıklandı.", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

    private fun getAllNote() {
        viewModel.noteList.observe(viewLifecycleOwner) { noteLists ->
            noteList = noteLists
            adapter.updateList(noteList)
            binding.apply {
                if (noteList.isEmpty()) {
                    noteReyclerView.visibility = View.GONE
                    animationView.visibility = View.VISIBLE
                    animationTextView.visibility = View.VISIBLE
                } else {
                    noteReyclerView.visibility = View.VISIBLE
                    animationView.visibility = View.GONE
                    animationTextView.visibility = View.GONE


                }

            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchWordCase(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchWordCase(newText)
        }
        return true
    }

    private fun searchWordCase(query: String) {
        val searchQuery = "%$query%"
        viewModel.searchWord(searchQuery)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



