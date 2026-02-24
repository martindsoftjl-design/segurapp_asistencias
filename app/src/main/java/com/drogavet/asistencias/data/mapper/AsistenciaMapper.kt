package com.drogavet.asistencias.data.mapper

import com.drogavet.asistencias.domain.model.Asistencia
import com.drogavet.asistencias.data.local.entity.AsistenciaEntity

fun AsistenciaEntity.toDomain(): Asistencia {
    return Asistencia(
        id = id, //
        empleadoId = empleadoId,
        fecha = fecha,
        horaEntrada = horaEntrada,
        horaSalida = horaSalida,
        estado = estado
    )
}

fun Asistencia.toEntity(): AsistenciaEntity {
    return AsistenciaEntity(
        id = id, //
        empleadoId = empleadoId,
        fecha = fecha,
        horaEntrada = horaEntrada,
        horaSalida = horaSalida,
        estado = estado
    )
}