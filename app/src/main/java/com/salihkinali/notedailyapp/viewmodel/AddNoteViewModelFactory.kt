package com.salihkinali.notedailyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salihkinali.notedailyapp.model.NoteDao

class AddNoteViewModelFactory(private val dao: NoteDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNoteViewModel::class.java)) {
            return AddNoteViewModel(dao) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class'ı")
    }
}