package com.drogavet.asistencias.data.local.dao

import androidx.room.*
import com.drogavet.asistencias.data.local.entity.AsistenciaEntity
import com.drogavet.asistencias.domain.model.AsistenciaConEmpleado
import kotlinx.coroutines.flow.Flow
@Dao
interface AsistenciaDao {
    @Insert
    suspend fun insertar(asistencia: AsistenciaEntity)
    @Update
    suspend fun actualizar(asistencia: AsistenciaEntity)
    @Delete
    suspend fun eliminar(asistencia: AsistenciaEntity)
    @Query("SELECT * FROM asistencias ORDER BY fecha DESC")
    fun obtenerTodas(): Flow<List<AsistenciaEntity>>

    @Query("SELECT * FROM asistencias WHERE empleadoId = :empleadoId")
    suspend fun obtenerPorEmpleado(empleadoId: Int): List<AsistenciaEntity>

    @Query("""
        SELECT * FROM asistencias 
        WHERE empleadoId = :empleadoId 
        AND fecha = :fecha 
        LIMIT 1
    """)
    suspend fun obtenerAsistenciaPorFecha(
        empleadoId: Int,
        fecha: String
    ): AsistenciaEntity?

    @Query("SELECT COUNT(*) FROM asistencias WHERE fecha = :fecha")
    suspend fun contarAsistenciasPorFecha(fecha: String): Int

    @Query("""
        SELECT COUNT(*) FROM asistencias 
        WHERE fecha = :fecha 
        AND estado = 'TARDANZA'
    """)
    suspend fun contarTardanzasPorFecha(fecha: String): Int

    @Query("""
        SELECT COUNT(*) FROM asistencias 
        WHERE fecha = :fecha 
        AND estado = 'FALTA'
    """)
    suspend fun contarFaltasPorFecha(fecha: String): Int

    @Query("""
    SELECT a.id, a.empleadoId, e.nombre, e.apellido,
           a.fecha, a.horaEntrada, a.horaSalida, a.estado
    FROM asistencias a
    INNER JOIN empleados e ON a.empleadoId = e.id
    ORDER BY a.fecha DESC
""")
    fun obtenerAsistenciasConEmpleado(): Flow<List<AsistenciaConEmpleado>>

}