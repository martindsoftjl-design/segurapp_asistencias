package com.drogavet.asistencias.ui.empleado

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.drogavet.asistencias.data.local.DatabaseProvider

class EmpleadoViewModel(application: Application) :
    AndroidViewModel(application) {

    private val dao =
        DatabaseProvider.getDatabase(application).empleadoDao()

    val listaEmpleados =
        dao.obtenerTodos().asLiveData()
}