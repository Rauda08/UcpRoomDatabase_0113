package com.example.ucp2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.entity.Dosen

@Database(entities = [Dosen::class], version = 1, exportSchema = false)
abstract class KrsDatabase : RoomDatabase(){
    abstract fun dosenDao(): DosenDao
}
