package com.example.transportistaapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "rutas_table")
data class RutaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("transportista") val transportista: String,
    @ColumnInfo("alias") val alias: String,
    @ColumnInfo("activa") val activa: Boolean,
    @ColumnInfo("cargado") val cargado: Boolean,
    @ColumnInfo("completado") val completado: Boolean,

)
