package com.example.transportistaapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.transportistaapp.data.database.entities.PaqueteEntity
import java.util.Date


@Dao
interface PaqueteDao {

    @Query("Select * FROM paquetes_table ORDER BY ruta DESC")
    suspend fun getAll():List<PaqueteEntity>

    @Query("Select * FROM paquetes_table WHERE ruta=:ruta")
    suspend fun obtenerPorRuta(ruta:Int):List<PaqueteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(paquetes:List<PaqueteEntity>)

    @Query("UPDATE paquetes_table SET fechaEntrega=:fechaEntrega, estado=3 WHERE id=:paqueteId")
    suspend fun entregar(paqueteId:String, fechaEntrega:Date)

    @Query("UPDATE paquetes_table SET estado=4 WHERE id=:paqueteId")
    suspend fun entregaFallida(paqueteId:String)

    @Query("DELETE FROM paquetes_table")
    suspend fun deleteAll()
}

