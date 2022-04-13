package com.salihkinali.notedailyapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salihkinali.notedailyapp.model.NoteDao

class NoteViewModelFactory (
    private val dataSource: NoteDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Bilinmeyen ViewModel class'ı")
    }
}