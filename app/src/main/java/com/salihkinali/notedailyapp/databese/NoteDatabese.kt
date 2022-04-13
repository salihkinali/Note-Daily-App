package com.salihkinali.notedailyapp.databese

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.salihkinali.notedailyapp.model.NoteDao
import com.salihkinali.notedailyapp.model.NoteModel

@Database(entities = [NoteModel::class], version = 1, exportSchema = false)
abstract class NoteDatabese : RoomDatabase(){

    abstract val noteDatabeseDao: NoteDao

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