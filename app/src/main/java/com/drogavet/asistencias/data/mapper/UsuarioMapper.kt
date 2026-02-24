package com.drogavet.asistencias.data.mapper

import com.drogavet.asistencias.data.local.entity.UsuarioEntity
import com.drogavet.asistencias.domain.model.Usuario

fun UsuarioEntity.toDomain(): Usuario {
    return Usuario(
        id = id,
        nombre = nombre,
        rol = rol,
        email = email,
        telefono = telefono
    )
}