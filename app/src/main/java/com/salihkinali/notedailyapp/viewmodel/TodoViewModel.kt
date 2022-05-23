package com.salihkinali.notedailyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihkinali.notedailyapp.model.TodoDao
import com.salihkinali.notedailyapp.model.TodoModel
import kotlinx.coroutines.launch
import java.text.FieldPosition

class TodoViewModel(private val dao: TodoDao) : ViewModel() {
    private var _todoList = MutableLiveData<List<TodoModel>>()
    val todoList: LiveData<List<TodoModel>> get() = _todoList

    init {
        getTodo()
    }

    private fun getTodo() {
        viewModelScope.launch {
            _todoList.value = dao.readAllNote()
        }
    }

    fun insertTodo(todo: TodoModel){
        viewModelScope.launch {
            dao.insertTodo(todo)
            _todoList.value =dao.readAllNote()
        }
    }

    fun deleteNote(position: Int) {
        viewModelScope.launch {
            dao.deleteItem(position)
            getTodo()
        }
    }
}