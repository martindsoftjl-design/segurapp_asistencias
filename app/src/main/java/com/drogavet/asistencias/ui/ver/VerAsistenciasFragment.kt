package com.drogavet.asistencias.ui.ver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drogavet.asistencias.R
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class VerAsistenciasFragment : Fragment() {

    private val vm: AsistenciasViewModel by viewModels()
    private var fechaInterna: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_ver_asistencias, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.iniciar(requireContext())

        val rv = view.findViewById<RecyclerView>(R.id.rvAsistencias)
        val tvFecha = view.findViewById<TextView>(R.id.tvFecha)

        val adapter = AsistenciaAdapter(emptyList())
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        // Mostrar fecha actual al iniciar
        val formatterVista = SimpleDateFormat("dd MMMM yyyy", Locale("es", "ES"))
        val formatterISO = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val hoy = Date()
        tvFecha.text = formatterVista.format(hoy)
        fechaInterna = formatterISO.format(hoy)

// DatePicker
        configurarDatePicker(tvFecha, rv)

        // Observador
        vm.asistencias.observe(viewLifecycleOwner) { list ->
            // Filtrar por fecha seleccionada
            val listaFiltrada = list.filter { it.fecha == fechaInterna }
            adapter.submit(listaFiltrada)
        }
    }

    private fun configurarDatePicker(tvFecha: TextView, rv: RecyclerView) {

        tvFecha.setOnClickListener {

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Seleccionar fecha")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.show(parentFragmentManager, "DATE_PICKER_VER")

            datePicker.addOnPositiveButtonClickListener { selection ->

                val utc = TimeZone.getTimeZone("UTC")

                // Formato para mostrar bonito al usuario
                val formatoVista = SimpleDateFormat("dd MMMM yyyy", Locale("es", "ES"))
                formatoVista.timeZone = utc
                tvFecha.text = formatoVista.format(Date(selection))

                // Formato ISO para filtrar en Room
                val formatoISO = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                formatoISO.timeZone = utc
                fechaInterna = formatoISO.format(Date(selection))

                // Actualizar lista automáticamente al cambiar fecha
                vm.asistencias.value?.let { list ->
                    val listaFiltrada = list.filter { it.fecha == fechaInterna }
                    (rv.adapter as? AsistenciaAdapter)?.submit(listaFiltrada)
                }
            }
        }
    }
}