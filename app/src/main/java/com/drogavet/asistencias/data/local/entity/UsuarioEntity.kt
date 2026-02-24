package com.drogavet.asistencias.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class UsuarioEntity(

    @PrimaryKey
    val id: Int=0,
    val nombre: String,
    val rol: String,
    val email: String,
    val telefono: String,
    val password: String
)