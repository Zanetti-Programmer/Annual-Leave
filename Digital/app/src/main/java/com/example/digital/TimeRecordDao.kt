package com.example.digital

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TimeRecordDao {

    @Query("SELECT * FROM time_records")
    fun getAll(): List<TimeRecord>

    // Outros métodos do DAO, como insert, update, delete, etc.
}
