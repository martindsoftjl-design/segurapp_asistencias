package com.drogavet.asistencias.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "empleados")
data class EmpleadoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dni: String,
    val nombre: String,
    val apellido: String,
    val cargo: String,
    val activo: Boolean = true
)