package com.salihkinali.notedailyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
class TodoModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "todo_title")
    val addTodo: String,
    @ColumnInfo(name = "todo_date")
    val dateTodo: String,
) {
}