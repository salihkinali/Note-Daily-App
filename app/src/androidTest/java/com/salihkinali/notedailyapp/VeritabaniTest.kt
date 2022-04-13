package com.salihkinali.notedailyapp

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.salihkinali.notedailyapp.databese.NoteDatabese
import com.salihkinali.notedailyapp.model.NoteDao
import com.salihkinali.notedailyapp.model.NoteModel
import org.junit.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


/**
 * This is not meant to be a full set of tests. For simplicity, most of your samples do not
 * include tests. However, when building the Room, it is helpful to make sure it works before
 * adding the UI.
 */

@RunWith(AndroidJUnit4::class)
class VeritabaniTest {

    private lateinit var noteDao: NoteDao
    private lateinit var db: NoteDatabese

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, NoteDatabese::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        noteDao = db.noteDatabeseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    @Test
    @Throws(Exception::class)
    fun insertAndGetNight() {
        print("New project is too close ")
    }
}
