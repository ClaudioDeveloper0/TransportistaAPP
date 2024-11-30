package com.example.transportistaapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "paquetes_table")
data class PaqueteEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id : String,
    @ColumnInfo(name = "receptor") val receptor : String,
    @ColumnInfo(name = "fono") val fono : String,
    @ColumnInfo(name = "direccion") val direccion : String,
    @ColumnInfo(name = "estado") val estado : String,
    @ColumnInfo(name = "ruta") val ruta : String,
)
