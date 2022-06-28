package com.salihkinali.notedailyapp.view.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
            StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.noteReyclerView.adapter = adapter
        getAllNote()
        adapter.onNoteClick = { notePosition ->
            viewModel.deleteNote(notePosition)
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


    private fun getAllNote() {
        viewModel.noteList.observe(viewLifecycleOwner) { noteLists ->
            noteList = noteLists
            adapter.updateList(noteList)
            binding.apply {
                if (noteList.isEmpty()) {
                    hideAnimation()
                } else {
                    showAnimation()
                }
            }
        }
    }

    private fun showAnimation() {
        binding.apply {
            noteReyclerView.visibility = View.VISIBLE
            animationView.visibility = View.GONE
            animationTextView.visibility = View.GONE
        }
    }

    private fun hideAnimation() {
        binding.apply {
            noteReyclerView.visibility = View.GONE
            animationView.visibility = View.VISIBLE
            animationTextView.visibility = View.VISIBLE
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
        } else {
            binding.animationTextView.text = "Could not find where you are looking for"
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



