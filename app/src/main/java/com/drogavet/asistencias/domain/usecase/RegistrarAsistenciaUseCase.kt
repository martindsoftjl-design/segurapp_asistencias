package com.drogavet.asistencias.domain.usecase

import com.drogavet.asistencias.domain.model.Asistencia
import com.drogavet.asistencias.data.repository.AsistenciaRepository
import com.drogavet.asistencias.domain.manager.AsistenciaCalculator

class RegistrarAsistenciaUseCase(
    private val repository: AsistenciaRepository,
    private val calculator: AsistenciaCalculator
) {

    suspend fun ejecutar(
        empleadoId: Int,
        fecha: String,
        horaActual: String
    ): Resultado {

        val asistenciaExistente =
            repository.obtenerPorFecha(empleadoId, fecha)

        if (asistenciaExistente != null) {
            return Resultado.YaExiste
        }

        val estadoCalculado =
            calculator.calcularEstado(horaActual)

        val nuevaAsistencia = Asistencia(
            id = 0,
            empleadoId = empleadoId,
            fecha = fecha,
            horaEntrada = horaActual,
            horaSalida = null,
            estado = estadoCalculado
        )

        repository.insertar(nuevaAsistencia)

        return Resultado.Exito
    }

    suspend fun registrarSalida(
        empleadoId: Int,
        fecha: String,
        horaSalida: String
    ): Resultado {

        val asistenciaExistente =
            repository.obtenerPorFecha(empleadoId, fecha)

        if (asistenciaExistente == null) {
            return Resultado.NoExiste
        }

        if (asistenciaExistente.horaSalida != null) {
            return Resultado.SalidaYaRegistrada
        }

        val asistenciaActualizada =
            asistenciaExistente.copy(
                horaSalida = horaSalida
            )

        repository.actualizar(asistenciaActualizada)

        return Resultado.Exito
    }


    sealed class Resultado {
        object Exito : Resultado()
        object YaExiste : Resultado()
        object NoExiste : Resultado()
        object SalidaYaRegistrada : Resultado()
    }


}

