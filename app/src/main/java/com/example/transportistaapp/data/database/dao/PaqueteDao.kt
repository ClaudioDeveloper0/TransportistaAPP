package com.example.transportistaapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.transportistaapp.data.database.entities.PaqueteEntity


@Dao
interface PaqueteDao {

    @Query("Select * FROM paquetes_table ORDER BY ruta DESC")
    suspend fun getAll():List<PaqueteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(paquetes:List<PaqueteEntity>)
}