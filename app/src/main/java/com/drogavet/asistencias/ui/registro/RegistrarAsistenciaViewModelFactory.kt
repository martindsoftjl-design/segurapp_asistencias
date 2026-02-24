package com.drogavet.asistencias.ui.registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.drogavet.asistencias.domain.usecase.RegistrarAsistenciaUseCase

class RegistrarAsistenciaViewModelFactory(
    private val useCase: RegistrarAsistenciaUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistrarAsistenciaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistrarAsistenciaViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}