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

    @Query("Select * FROM rutas_table WHERE en_reparto=1")
    suspend fun rutasEnReparto():List<RutaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(paquetes:List<RutaEntity>)

    @Query("DELETE FROM rutas_table")
    suspend fun deleteAll()
}