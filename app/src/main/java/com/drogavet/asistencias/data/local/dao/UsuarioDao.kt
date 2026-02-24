package com.drogavet.asistencias.data.local.dao

import androidx.room.*
import com.drogavet.asistencias.data.local.entity.UsuarioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(usuario: UsuarioEntity)

    @Query("SELECT * FROM usuarios")
    fun obtenerTodos(): Flow<List<UsuarioEntity>>

    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): UsuarioEntity?

    @Query("SELECT COUNT(*) FROM usuarios")
    suspend fun contarUsuarios(): Int

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun obtenerPorEmail(email: String, password: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UsuarioEntity?

}