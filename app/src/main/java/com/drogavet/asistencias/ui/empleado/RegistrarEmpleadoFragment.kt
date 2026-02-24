package com.drogavet.asistencias.ui.empleado

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.drogavet.asistencias.data.local.DatabaseProvider
import com.drogavet.asistencias.data.local.entity.EmpleadoEntity
import com.drogavet.asistencias.databinding.FragmentRegistrarEmpleadoBinding
import kotlinx.coroutines.launch

class RegistrarEmpleadoFragment : Fragment() {

    private var _binding: FragmentRegistrarEmpleadoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrarEmpleadoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val empleadoDao = DatabaseProvider
            .getDatabase(requireContext())
            .empleadoDao()

        binding.btnGuardarEmpleado.setOnClickListener {

            val dni = binding.etDni.text.toString().trim()
            val nombre = binding.etNombre.text.toString().trim()
            val apellido = binding.etApellido.text.toString().trim()
            val cargo = binding.etCargo.text.toString().trim()

            if (dni.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || cargo.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val empleado = EmpleadoEntity(
                dni = dni,
                nombre = nombre,
                apellido = apellido,
                cargo = cargo
            )

            lifecycleScope.launch {
                empleadoDao.insertar(empleado)

                Toast.makeText(requireContext(), "Empleado registrado ✅", Toast.LENGTH_SHORT).show()

                limpiarCampos()
            }
        }
    }

    private fun limpiarCampos() {
        binding.etDni.text?.clear()
        binding.etNombre.text?.clear()
        binding.etApellido.text?.clear()
        binding.etCargo.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}