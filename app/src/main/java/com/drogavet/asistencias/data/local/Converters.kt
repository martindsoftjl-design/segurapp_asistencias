package com.drogavet.asistencias.data.local

import androidx.room.TypeConverter
import com.drogavet.asistencias.domain.model.EstadoAsistencia

class Converters {

    @TypeConverter
    fun fromEstado(value: EstadoAsistencia): String {
        return value.name
    }

    @TypeConverter
    fun toEstado(value: String): EstadoAsistencia {
        return EstadoAsistencia.valueOf(value)
    }
}