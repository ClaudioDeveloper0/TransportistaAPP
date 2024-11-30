package com.example.transportistaapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.transportistaapp.data.database.entities.RutaEntity


@Dao
interface RutaDao {

    @Query("Select * FROM rutas_table")
    suspend fun getAll():List<RutaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(paquetes:List<RutaEntity>)
}