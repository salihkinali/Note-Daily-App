package com.salihkinali.notedailyapp.view.fragment

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.adapter.TodoAdapter
import com.salihkinali.notedailyapp.databese.NoteDatabese
import com.salihkinali.notedailyapp.databinding.FragmentAddListBinding
import com.salihkinali.notedailyapp.model.TodoModel
import com.salihkinali.notedailyapp.viewmodel.TodoViewModel
import com.salihkinali.notedailyapp.viewmodel.TodoViewModelFactory
import java.util.*


class AddListFragment : Fragment() {
    private var _binding: FragmentAddListBinding? = null
    private lateinit var db: NoteDatabese
    private lateinit var viewModel: TodoViewModel
    private val adapter by lazy { TodoAdapter() }
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        db = NoteDatabese.getInstance(requireContext())!!
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddListBinding.inflate(inflater, container, false)

        val dbDao = NoteDatabese.getInstance(requireContext())?.todoDatabasedDao

        val viewModelFactory = dbDao?.let { TodoViewModelFactory(it) }

        viewModel = viewModelFactory?.let {
            ViewModelProvider(this, it)[TodoViewModel::class.java]
        }!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Adapter bağlama işi burda yapılacak
        binding.addTodoRecyclerView.layoutManager =
            LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding.addTodoRecyclerView.adapter = adapter
        viewModel.todoList.observe(viewLifecycleOwner) {
           adapter.submitList(it)
        }
        adapter.onTodoClick = {
            viewModel.deleteNote(it)
        }
        binding.todoActionButton.setOnClickListener {
            createAlertView()
        }


    }

    private fun createAlertView() {
        val design = layoutInflater.inflate(R.layout.addlist_design, null)
        val alertEditText = design.findViewById<EditText>(R.id.addTodoEditText)
        val alertDateTime = design.findViewById<EditText>(R.id.addDateTime)
        val alertView = AlertDialog.Builder(requireContext())
        alertView.setTitle("Todo List Screen")
        alertView.setIcon(R.drawable.ic_check)
        alertView.setView(design)
        alertDateTime.setOnClickListener {

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePicker = DatePickerDialog(requireContext(), { datePicker, yil, ay, gun ->
                alertDateTime.setText("$gun/${ay + 1}/$yil")
            }, year, month, day)

            datePicker.setTitle("Please Choise The Date Time")
            datePicker.setButton(DialogInterface.BUTTON_POSITIVE, "SET", datePicker)
            datePicker.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", datePicker)
            datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
            datePicker.show()

        }
        alertView.setPositiveButton("SET") { dialogInterface, i ->
            val getData = alertEditText.text.toString()
            val takeDate = alertDateTime.text.toString()
            if(getData.isNotEmpty() && takeDate.isNotEmpty()){
                viewModel.insertTodo(
                    TodoModel(addTodo = getData, dateTodo = takeDate)
                )
            }else{
                Toast.makeText(activity,"Please Fill in the Blanks",Toast.LENGTH_SHORT).show()
                return@setPositiveButton
            }

        }
        alertView.setNegativeButton("CANCEL") { dialogInterface, i ->
        }
        alertView.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}