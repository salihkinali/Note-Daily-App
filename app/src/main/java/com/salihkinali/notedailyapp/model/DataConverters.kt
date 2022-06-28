package com.salihkinali.notedailyapp.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class DataConverters {
    @TypeConverter
    fun convertByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    @TypeConverter
    fun convertBitmap(array: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(array, 0, array.size)
    }
}
