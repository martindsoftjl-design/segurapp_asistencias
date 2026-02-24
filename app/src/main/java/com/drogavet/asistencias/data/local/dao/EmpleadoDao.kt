package com.drogavet.asistencias.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import com.drogavet.asistencias.data.local.entity.EmpleadoEntity

@Dao
interface EmpleadoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(empleado: EmpleadoEntity)

    @Update
    suspend fun actualizar(empleado: EmpleadoEntity)

    @Query("SELECT * FROM empleados WHERE activo = :estado ORDER BY nombre ASC")
    fun obtenerPorEstado(estado: Boolean): Flow<List<EmpleadoEntity>>

    @Query("SELECT * FROM empleados ORDER BY nombre ASC")
    fun obtenerTodos(): Flow<List<EmpleadoEntity>>

    @Query("UPDATE empleados SET activo = 0 WHERE id = :id")
    suspend fun desactivar(id: Int)


}