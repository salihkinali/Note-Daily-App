package com.salihkinali.notedailyapp.databese

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salihkinali.notedailyapp.model.NoteDao
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.model.TodoDao
import com.salihkinali.notedailyapp.model.TodoModel

@Database(entities = [NoteModel::class,TodoModel::class], version = 2, exportSchema = false)
abstract class NoteDatabese : RoomDatabase(){

    abstract val noteDatabeseDao: NoteDao
    abstract val todoDatabasedDao: TodoDao

    companion object{
       private var INSTANCE: NoteDatabese? = null

        fun getInstance(context: Context):NoteDatabese?{
            var instance = INSTANCE
            if(instance == null){
                instance = Room.databaseBuilder(
                        context.applicationContext,
                NoteDatabese::class.java,
                "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return INSTANCE
        }

    }
}