package com.salihkinali.notedailyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihkinali.notedailyapp.model.NoteDao
import com.salihkinali.notedailyapp.model.NoteModel
import kotlinx.coroutines.launch

class AddNoteViewModel(private val dao:NoteDao): ViewModel() {
    private var _selectedNoteColor:String? = "#282829"
    val selectedNoteColor: String get() = _selectedNoteColor!!
   private var _selectedRadioState:String? = "Eğitim"
    val selectedRadioState:String get() = _selectedRadioState!!
    init {
        viewModelScope.launch {}
    }
    fun addNote(note: NoteModel) {
        viewModelScope.launch {
            dao.insertNote(note)
        }
    }

    fun choiseOne() {
        _selectedNoteColor = "#282829"
    }

    fun choseTwo() {
        _selectedNoteColor = "#007C3F"
    }

    fun choiseThree() {
        _selectedNoteColor = "#F6F54D"
    }

    fun choiseFour() {
        _selectedNoteColor = "#344CB7"
    }

    fun choiseFive() {
        _selectedNoteColor = "#F55353"
    }

    fun selectONe() {
        _selectedRadioState = "Eğitim"
    }

    fun selectTwo() {
        _selectedRadioState = "Yaşam"
    }
    fun selectThree() {
        _selectedRadioState = "Eğlence"
    }
    fun selectFour() {
        _selectedRadioState = "Diğer"
    }

}


