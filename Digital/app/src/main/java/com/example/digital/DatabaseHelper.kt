package com.example.digital

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun insertTimeEntry(entryTime: Date, lunchOutTime: Date, lunchInTime: Date, exitTime: Date) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_DATE, SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()))
            put(COLUMN_ENTRY_TIME, SimpleDateFormat("HH:mm", Locale.getDefault()).format(entryTime))
            put(COLUMN_LUNCH_OUT_TIME, SimpleDateFormat("HH:mm", Locale.getDefault()).format(lunchOutTime))
            put(COLUMN_LUNCH_IN_TIME, SimpleDateFormat("HH:mm", Locale.getDefault()).format(lunchInTime))
            put(COLUMN_EXIT_TIME, SimpleDateFormat("HH:mm", Locale.getDefault()).format(exitTime))
        }
        db.insert(TABLE_NAME, null, values)
    }



    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "TimeEntries.db"
        const val TABLE_NAME = "time_entries"
        const val COLUMN_ID = "id"
        const val COLUMN_DATE = "date"
        const val COLUMN_ENTRY_TIME = "entry_time"
        const val COLUMN_LUNCH_OUT_TIME = "lunch_out_time"
        const val COLUMN_LUNCH_IN_TIME = "lunch_in_time"
        const val COLUMN_EXIT_TIME = "exit_time"

        private const val SQL_CREATE_ENTRIES = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_DATE TEXT,
                $COLUMN_ENTRY_TIME TEXT,
                $COLUMN_LUNCH_OUT_TIME TEXT,
                $COLUMN_LUNCH_IN_TIME TEXT,
                $COLUMN_EXIT_TIME TEXT
            )
        """

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
    fun getAllTimeRecords(): List<TimeRecord> {
        val timeRecords = mutableListOf<TimeRecord>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        try {
            cursor?.let {
                if (cursor.moveToFirst()) {
                    do {
                        val id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID))
                        val date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE))
                        val entryTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENTRY_TIME))
                        val lunchOutTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LUNCH_OUT_TIME))
                        val lunchInTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LUNCH_IN_TIME))
                        val exitTime = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXIT_TIME))

                        val timeRecord = TimeRecord(id, date, entryTime, lunchOutTime, lunchInTime, exitTime)
                        timeRecords.add(timeRecord)
                    } while (cursor.moveToNext())
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }

        return timeRecords
    }

}
