package com.drogavet.asistencias.domain.model



data class AsistenciaConEmpleado(
    val id: Int,
    val empleadoId: Int,
    val nombre: String,
    val apellido: String,
    val fecha: String,
    val horaEntrada: String?,
    val horaSalida: String?,
    val estado: EstadoAsistencia
)