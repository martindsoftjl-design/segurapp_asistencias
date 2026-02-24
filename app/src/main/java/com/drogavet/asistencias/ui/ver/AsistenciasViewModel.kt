package com.drogavet.asistencias.ui.ver

import android.content.Context
import androidx.lifecycle.*
import com.drogavet.asistencias.data.local.DatabaseProvider
import com.drogavet.asistencias.domain.model.AsistenciaConEmpleado
import kotlinx.coroutines.launch

class AsistenciasViewModel : ViewModel() {

    private val _asistencias =
        MutableLiveData<List<AsistenciaConEmpleado>>()

    val asistencias:
            LiveData<List<AsistenciaConEmpleado>> = _asistencias

    fun iniciar(context: Context) {

        val dao = DatabaseProvider
            .getDatabase(context)
            .asistenciaDao()

        viewModelScope.launch {

            dao.obtenerAsistenciasConEmpleado()
                .collect { lista ->

                    _asistencias.postValue(lista)
                }
        }
    }
}