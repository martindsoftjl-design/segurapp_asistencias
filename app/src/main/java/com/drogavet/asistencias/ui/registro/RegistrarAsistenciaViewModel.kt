package com.drogavet.asistencias.ui.registro

import androidx.lifecycle.*
import com.drogavet.asistencias.domain.usecase.RegistrarAsistenciaUseCase
import kotlinx.coroutines.launch

class RegistrarAsistenciaViewModel(
    private val useCase: RegistrarAsistenciaUseCase
) : ViewModel() {

    private val _estadoRegistro =
        MutableLiveData<RegistrarAsistenciaUseCase.Resultado?>()
    val estadoRegistro:
            LiveData<RegistrarAsistenciaUseCase.Resultado?> = _estadoRegistro

    fun registrarEntrada(
        empleadoId: Int,
        fecha: String,
        horaActual: String
    ) {
        viewModelScope.launch {
            val resultado =
                useCase.ejecutar(empleadoId, fecha, horaActual)
            _estadoRegistro.value = resultado
        }
    }

    fun registrarSalida(
        empleadoId: Int,
        fecha: String,
        horaActual: String
    ) {
        viewModelScope.launch {

            val resultado =
                useCase.registrarSalida(empleadoId, fecha, horaActual)

            _estadoRegistro.value = resultado
        }
    }

    fun limpiarEstado() {
        _estadoRegistro.value = null
    }


}