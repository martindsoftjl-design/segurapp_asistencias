package com.drogavet.asistencias.data.repository

import com.drogavet.asistencias.data.local.dao.AsistenciaDao
import com.drogavet.asistencias.data.mapper.toDomain
import com.drogavet.asistencias.data.mapper.toEntity
import com.drogavet.asistencias.domain.model.Asistencia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AsistenciaRepository(
    private val asistenciaDao: AsistenciaDao
) {
    suspend fun obtenerPorFecha(
        empleadoId: Int,
        fecha: String
    ): Asistencia? {
        return asistenciaDao
            .obtenerAsistenciaPorFecha(empleadoId, fecha)
            ?.toDomain()
    }

    fun obtenerTodas(): Flow<List<Asistencia>> {
        return asistenciaDao.obtenerTodas()
            .map { lista -> lista.map { it.toDomain() } }
    }
    suspend fun insertar(asistencia: Asistencia) {
        asistenciaDao.insertar(asistencia.toEntity())
    }
    suspend fun actualizar(asistencia: Asistencia) {
        asistenciaDao.actualizar(asistencia.toEntity())
    }
}