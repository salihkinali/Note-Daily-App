package com.salihkinali.notedailyapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.salihkinali.notedailyapp.model.NoteDao
import com.salihkinali.notedailyapp.model.NoteModel
import kotlinx.coroutines.launch

class NoteViewModel(val dbDao: NoteDao, application: Application) : AndroidViewModel(application) {
    private var _noteList = MutableLiveData<List<NoteModel>>()
    val noteList: LiveData<List<NoteModel>> get() = _noteList

    init {
        getNotes()
    }

    private fun getNotes() {

        viewModelScope.launch {
            _noteList.value = dbDao.readAllNote()
        }
    }

    fun addNote(note: NoteModel){
        viewModelScope.launch {
            dbDao.insertNote(note)
        }
    }

    fun updateNote(note: NoteModel){
        viewModelScope.launch {
            dbDao.updateNote(note)
            getNotes()
        }
    }
    fun deleteNote(note: NoteModel){
        viewModelScope.launch {
            dbDao.deleteNote(note)
            getNotes()
        }
    }
}