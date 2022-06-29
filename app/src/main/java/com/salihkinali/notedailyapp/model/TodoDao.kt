package com.salihkinali.notedailyapp.model

import androidx.room.*

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: TodoModel)

    @Update
    suspend fun updateNote(todo: TodoModel)

    @Delete
    suspend  fun deleteNote(todo: TodoModel)

    @Query("SELECT * FROM todo_table ORDER BY todo_date ASC")
    suspend fun readAllNote(): List<TodoModel>

    @Query("DELETE FROM todo_table WHERE id= :position")
    suspend fun deleteItem(position: Int)
}