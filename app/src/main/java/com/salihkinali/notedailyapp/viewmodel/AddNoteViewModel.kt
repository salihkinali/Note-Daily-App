package com.salihkinali.notedailyapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salihkinali.notedailyapp.model.Constants
import com.salihkinali.notedailyapp.model.NoteDao
import com.salihkinali.notedailyapp.model.NoteModel
import kotlinx.coroutines.launch

class AddNoteViewModel(private val dao:NoteDao): ViewModel() {

    private var _selectedNoteColor:String? = Constants.BLACK_COLOR
    val selectedNoteColor: String get() = _selectedNoteColor!!
   private var _selectedRadioState:String? = Constants.EDUCATION
    val selectedRadioState:String get() = _selectedRadioState!!
    init {
        viewModelScope.launch {}
    }
    fun addNote(note: NoteModel) {
        viewModelScope.launch {
            dao.insertNote(note)
        }
    }

    fun chooseOne() {
        _selectedNoteColor =Constants.BLACK_COLOR
    }

    fun choseTwo() {
        _selectedNoteColor = Constants.GREEN_COLOR
    }

    fun chooseThree() {
        _selectedNoteColor = Constants.YELLOW_COLOR
    }

    fun chooseFour() {
        _selectedNoteColor = Constants.BLUE_COLOR
    }

    fun chooseFive() {
        _selectedNoteColor = Constants.PINK_COLOR
    }

    fun selectONe() {
        _selectedRadioState = Constants.EDUCATION
    }

    fun selectTwo() {
        _selectedRadioState = Constants.LIFE
    }
    fun selectThree() {
        _selectedRadioState = Constants.FUN
    }
    fun selectFour() {
        _selectedRadioState = Constants.ANOTHER
    }

}


