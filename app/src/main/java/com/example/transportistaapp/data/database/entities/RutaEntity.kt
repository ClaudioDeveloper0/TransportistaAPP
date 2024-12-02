package com.example.transportistaapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.transportistaapp.domain.model.Ruta


@Entity(tableName = "rutas_table")
data class RutaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id") val id: Int = 0,
    @ColumnInfo("alias") val alias: String,
    @ColumnInfo("en_reparto") val enReparto: Boolean,
    @ColumnInfo("cargado") val cargado: Boolean,
    @ColumnInfo("completado") val completado: Boolean,
)

fun RutaEntity.toDomain() : Ruta {
    return Ruta(
        id = id.toString().padStart(10, '0'),
        nombre = alias,
        cargado = cargado,
        enReparto = enReparto,
        completado = completado
    )
}
fun Ruta.toRoom() :RutaEntity {
    return RutaEntity(
        id = id.toInt(),
        alias = nombre,
        enReparto = enReparto,
        cargado = cargado,
        completado = completado
    )
}