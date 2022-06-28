package com.salihkinali.notedailyapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.ByteArrayOutputStream
import java.io.Serializable

@Entity(tableName = "notes_table")
data class NoteModel(
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0,
    @ColumnInfo(name = "note_title")
    var noteTitle: String,
    @ColumnInfo(name = "note_category")
    var noteCategory: String,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    var noteImage:ByteArray? = null,
    @ColumnInfo(name = "note_inside")
    var noteInside: String,
    @ColumnInfo(name = "note_color")
    var noteColor :String,
    @ColumnInfo(name = "date_time")
    var dateTime : String,
    @ColumnInfo(name = "time_now")
    var timeNow : String
):Serializable {

}
