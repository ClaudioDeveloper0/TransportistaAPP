package com.example.transportistaapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.transportistaapp.data.database.dao.*
import com.example.transportistaapp.data.database.entities.*


@Database(
    entities = [
        PaqueteEntity::class,
        RutaEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getPaqueteDao():PaqueteDao

    abstract fun getRutaDao():RutaDao
}