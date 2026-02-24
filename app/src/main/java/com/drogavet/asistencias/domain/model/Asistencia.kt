package com.drogavet.asistencias.domain.model

data class Asistencia(
    val id: Int,
    val empleadoId: Int,
    val fecha: String,
    val horaEntrada: String?,
    val horaSalida: String?,
    val estado: EstadoAsistencia
)