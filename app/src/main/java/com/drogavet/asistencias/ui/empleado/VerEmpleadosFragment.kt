package com.drogavet.asistencias.ui.empleado

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.drogavet.asistencias.R
import com.drogavet.asistencias.databinding.FragmentVerEmpleadosBinding

class VerEmpleadosFragment : Fragment(R.layout.fragment_ver_empleados) {

    private var _binding: FragmentVerEmpleadosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EmpleadoViewModel by viewModels()
    private lateinit var adapter: EmpleadoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentVerEmpleadosBinding.bind(view)

        adapter = EmpleadoAdapter(emptyList())

        binding.rvEmpleados.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvEmpleados.adapter = adapter

        viewModel.listaEmpleados.observe(viewLifecycleOwner) { empleados ->
            adapter.actualizarLista(empleados)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()


        _binding = null
    }
}