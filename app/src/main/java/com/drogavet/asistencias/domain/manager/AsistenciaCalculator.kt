package com.drogavet.asistencias.domain.manager

import com.drogavet.asistencias.domain.model.EstadoAsistencia

class AsistenciaCalculator {

    fun calcularEstado(horaEntrada: String): EstadoAsistencia {

        val horaLimite = "08:15" // ejemplo

        return if (horaEntrada <= horaLimite) {
            EstadoAsistencia.A_TIEMPO
        } else {
            EstadoAsistencia.TARDANZA
        }
    }
}