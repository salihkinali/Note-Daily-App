package com.salihkinali.notedailyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salihkinali.notedailyapp.model.TodoDao

class TodoViewModelFactory(private val dao: TodoDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(dao) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class'ı")
    }
}