package com.drogavet.asistencias.ui.registro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.drogavet.asistencias.data.local.DatabaseProvider
import com.drogavet.asistencias.data.local.entity.EmpleadoEntity
import com.drogavet.asistencias.data.repository.AsistenciaRepository
import com.drogavet.asistencias.databinding.FragmentRegistrarAsistenciaBinding
import com.drogavet.asistencias.domain.manager.AsistenciaCalculator
import com.drogavet.asistencias.domain.usecase.RegistrarAsistenciaUseCase
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
class RegistrarAsistenciaFragment : Fragment() {
    private var _binding: FragmentRegistrarAsistenciaBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegistrarAsistenciaViewModel
    private var fechaSeleccionadaISO: String = ""
    private var listaEmpleados: List<EmpleadoEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrarAsistenciaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fecha actual por defecto
        val today = Date()
        val formatterMostrar = SimpleDateFormat("dd MMMM yyyy", Locale("es", "ES"))
        val formatterInterno = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        fechaSeleccionadaISO = formatterInterno.format(today)
        binding.txtFechaSeleccionada.text = formatterMostrar.format(today)

        mostrarFechaHoraActual()
        configurarDatePicker()

        val database = DatabaseProvider.getDatabase(requireContext())
        val asistenciaDao = database.asistenciaDao()
        val empleadoDao = database.empleadoDao()

        // MVVM
        val repository = AsistenciaRepository(asistenciaDao)
        val calculator = AsistenciaCalculator()
        val useCase = RegistrarAsistenciaUseCase(repository, calculator)
        val factory = RegistrarAsistenciaViewModelFactory(useCase)

        viewModel = ViewModelProvider(this, factory)[RegistrarAsistenciaViewModel::class.java]

        // Observar resultado
        viewModel.estadoRegistro.observe(viewLifecycleOwner) { resultado ->

            resultado ?: return@observe

            when (resultado) {

                is RegistrarAsistenciaUseCase.Resultado.Exito -> {
                    Toast.makeText(
                        requireContext(),
                        "Operación realizada correctamente ✅",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is RegistrarAsistenciaUseCase.Resultado.YaExiste -> {
                    Toast.makeText(
                        requireContext(),
                        "Ya marcó entrada hoy",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is RegistrarAsistenciaUseCase.Resultado.NoExiste -> {
                    Toast.makeText(
                        requireContext(),
                        "Primero debe registrar entrada",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is RegistrarAsistenciaUseCase.Resultado.SalidaYaRegistrada -> {
                    Toast.makeText(
                        requireContext(),
                        "La salida ya fue registrada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            viewModel.limpiarEstado()
        }

        // Cargar empleados activos en Spinner
        lifecycleScope.launch {
            empleadoDao.obtenerTodos().collect { empleados ->
                listaEmpleados = empleados

                val nombres = listaEmpleados.map {
                    "${it.nombre} ${it.apellido}"
                }

                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    nombres
                )

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spUsuarios.adapter = adapter
            }
        }

        // Botón Registrar Entrada
        binding.btnEntrada.setOnClickListener {

            if (fechaSeleccionadaISO.isEmpty()) {
                Toast.makeText(requireContext(), "Selecciona una fecha", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val posicion = binding.spUsuarios.selectedItemPosition

            if (posicion < 0 || listaEmpleados.isEmpty()) {
                Toast.makeText(requireContext(), "Selecciona un empleado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val empleadoSeleccionado = listaEmpleados[posicion]

            viewModel.registrarEntrada(
                empleadoSeleccionado.id,
                fechaSeleccionadaISO,
                obtenerHoraActual()
            )
        }
        // Botón Registrar Salida
        binding.btnSalida.setOnClickListener {

            if (fechaSeleccionadaISO.isEmpty()) {
                Toast.makeText(requireContext(), "Selecciona una fecha", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val posicion = binding.spUsuarios.selectedItemPosition

            if (posicion < 0 || listaEmpleados.isEmpty()) {
                Toast.makeText(requireContext(), "Selecciona un empleado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val empleadoSeleccionado = listaEmpleados[posicion]

            viewModel.registrarSalida(
                empleadoSeleccionado.id,
                fechaSeleccionadaISO,
                obtenerHoraActual()
            )
        }

    }

    private fun mostrarFechaHoraActual() {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.txtFechaActual.text = formatoFecha.format(Date())

        val formatoHora = SimpleDateFormat("HH:mm a", Locale.getDefault())
        binding.txtHoraActual.text = formatoHora.format(Date())
    }

    private fun configurarDatePicker() {
        binding.cardFecha.setOnClickListener {

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Seleccionar fecha")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.show(parentFragmentManager, "DATE_PICKER_REGISTRAR")

            datePicker.addOnPositiveButtonClickListener { selection ->

                val utc = TimeZone.getTimeZone("UTC")

                val formatoVista = SimpleDateFormat("dd MMMM yyyy", Locale("es", "ES"))
                formatoVista.timeZone = utc
                binding.txtFechaSeleccionada.text = formatoVista.format(Date(selection))

                val formatoISO = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                formatoISO.timeZone = utc
                fechaSeleccionadaISO = formatoISO.format(Date(selection))
            }
        }
    }

    private fun obtenerHoraActual(): String {
        val formato = SimpleDateFormat("HH:mm", Locale.getDefault())
        return formato.format(Date())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}