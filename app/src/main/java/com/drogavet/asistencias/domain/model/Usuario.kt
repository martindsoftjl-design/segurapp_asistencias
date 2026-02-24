package com.drogavet.asistencias.domain.model

data class Usuario(
    val id: Int,
    val nombre: String,
    val rol: String,
    val email: String,
    val telefono: String
)