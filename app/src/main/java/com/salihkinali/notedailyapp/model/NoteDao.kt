package com.salihkinali.notedailyapp.model

import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteModel)

    @Update
    suspend fun updateNote(note: NoteModel)

    @Delete
    suspend  fun deleteNote(note: NoteModel)

    @Query("SELECT * FROM notes_table")
    suspend fun readAllNote(): List<NoteModel>

    @Query("SELECT * FROM notes_table WHERE note_title LIKE :searchQuery OR note_category LIKE :searchQuery")
    suspend fun searchData(searchQuery: String): List<NoteModel>

    @Query("SELECT * FROM notes_table ORDER BY note_title ASC")
    suspend fun shortFromAtoZ():List<NoteModel>

    @Query("SELECT * FROM notes_table ORDER BY note_category DESC")
    suspend fun shortUntilCategory():List<NoteModel>
}