package com.drogavet.asistencias.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.drogavet.asistencias.domain.model.EstadoAsistencia

@Entity(tableName = "asistencias")
data class AsistenciaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val empleadoId: Int,
    val fecha: String,
    val horaEntrada: String?,
    val horaSalida: String?,
    val estado: EstadoAsistencia
)