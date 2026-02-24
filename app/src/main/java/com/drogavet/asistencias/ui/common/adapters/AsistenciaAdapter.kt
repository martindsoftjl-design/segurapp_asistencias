package com.drogavet.asistencias.ui.common.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.drogavet.asistencias.R
import com.drogavet.asistencias.data.local.entity.EmpleadoEntity
import com.drogavet.asistencias.databinding.ItemAsistenciaBinding
import com.drogavet.asistencias.domain.model.Asistencia
import com.drogavet.asistencias.domain.model.EstadoAsistencia
import com.google.android.material.button.MaterialButton

class AsistenciaAdapter(
    private var lista: List<Asistencia>
) : RecyclerView.Adapter<AsistenciaAdapter.AsistenciaViewHolder>() {
    inner class AsistenciaViewHolder(val binding: ItemAsistenciaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsistenciaViewHolder {
        val binding = ItemAsistenciaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AsistenciaViewHolder(binding)
    }
    override fun onBindViewHolder(holder: AsistenciaViewHolder, position: Int) {

        val asistencia = lista[position]

        with(holder.binding) {

            // 👇 mostramos ID temporalmente
            tvNombre.text = "Empleado ID: ${asistencia.empleadoId}"

            tvFecha.text = asistencia.fecha
            tvEntrada.text = asistencia.horaEntrada ?: "--:--"
            tvSalida.text = asistencia.horaSalida ?: "--:--"

            btnEstado.text = asistencia.estado.name
        }
    }
    override fun getItemCount(): Int = lista.size

    fun submit(nuevaLista: List<Asistencia>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}