package com.drogavet.asistencias.ui.reportes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.drogavet.asistencias.databinding.FragmentReportesBinding
import com.drogavet.asistencias.domain.model.EstadoAsistencia
import com.drogavet.asistencias.ui.ver.AsistenciasViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class ReportesFragment : Fragment() {

    private var _binding: FragmentReportesBinding? = null
    private val binding get() = _binding!!

    private val vm: AsistenciasViewModel by viewModels()
    private var fechaInterna: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar ViewModel para cargar datos
        vm.iniciar(requireContext())

        // Fecha de hoy por defecto
        val today = Date()
        val formatterMostrar = SimpleDateFormat("dd MMMM yyyy", Locale("es", "ES"))
        val formatterInterno = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        fechaInterna = formatterInterno.format(today)
        binding.tvFecha.text = formatterMostrar.format(today)

        // DatePicker
        binding.tvFecha.setOnClickListener { mostrarDatePicker() }

        // Botón Buscar
        binding.btnBuscar.setOnClickListener { actualizarCards() }

        // Observer de asistencias
        vm.asistencias.observe(viewLifecycleOwner) {
            actualizarCards()
        }
    }

    private fun mostrarDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Selecciona una fecha")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        datePicker.show(parentFragmentManager, "DATE_PICKER")

        datePicker.addOnPositiveButtonClickListener { selection ->

            // Forzar UTC para que coincida con el registro
            val utc = TimeZone.getTimeZone("UTC")

            // Formato para mostrar bonito
            val formatterMostrar = SimpleDateFormat("dd MMMM yyyy", Locale("es", "ES"))
            formatterMostrar.timeZone = utc

            // Formato ISO para filtrar en Room
            val formatterISO = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            formatterISO.timeZone = utc

            val date = Date(selection)
            binding.tvFecha.text = formatterMostrar.format(date)
            fechaInterna = formatterISO.format(date)

            actualizarCards()
        }
    }

    private fun actualizarCards() {

        val listaFiltrada = vm.asistencias.value
            ?.filter { it.fecha == fechaInterna }
            ?: emptyList()

        val asistencias = listaFiltrada.count {
            it.estado == EstadoAsistencia.A_TIEMPO ||
                    it.estado == EstadoAsistencia.TARDANZA
        }

        val tardanzas = listaFiltrada.count {
            it.estado == EstadoAsistencia.TARDANZA
        }

        val faltas = listaFiltrada.count {
            it.estado == EstadoAsistencia.FALTA
        }

        binding.tvAsistencias.text = asistencias.toString()
        binding.tvTardanzas.text = tardanzas.toString()
        binding.tvFaltas.text = faltas.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}