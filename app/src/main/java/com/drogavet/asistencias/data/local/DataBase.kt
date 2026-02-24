package com.drogavet.asistencias.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.drogavet.asistencias.data.local.dao.AsistenciaDao
import com.drogavet.asistencias.data.local.dao.EmpleadoDao
import com.drogavet.asistencias.data.local.dao.UsuarioDao
import com.drogavet.asistencias.data.local.entity.AsistenciaEntity
import com.drogavet.asistencias.data.local.entity.UsuarioEntity
import com.drogavet.asistencias.data.local.entity.EmpleadoEntity
@Database(
    entities = [
        AsistenciaEntity::class,
        UsuarioEntity::class,
        EmpleadoEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun asistenciaDao(): AsistenciaDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun empleadoDao(): EmpleadoDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: android.content.Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "asistencias_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}