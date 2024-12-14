package com.example.transportistaapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.transportistaapp.data.database.dao.*
import com.example.transportistaapp.data.database.entities.*


@Database(
    entities = [
        PaqueteEntity::class,
        RutaEntity::class,
    ],
    version = 4,
    exportSchema = true,

)
@TypeConverters(Converter::class)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun getPaqueteDao():PaqueteDao

    abstract fun getRutaDao():RutaDao
}