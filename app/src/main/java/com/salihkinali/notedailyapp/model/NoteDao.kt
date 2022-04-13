package com.salihkinali.notedailyapp.model

import androidx.room.*

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(note: NoteModel)

    @Update
    suspend fun updateNote(note: NoteModel)

    @Delete
    suspend  fun deleteNote(note: NoteModel)

    @Query("SELECT * FROM notes_table")
    suspend fun readAllNote(): List<NoteModel>
}