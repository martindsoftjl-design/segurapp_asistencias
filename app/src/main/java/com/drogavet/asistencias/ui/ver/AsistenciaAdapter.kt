package com.drogavet.asistencias.ui.ver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.drogavet.asistencias.R
import com.drogavet.asistencias.databinding.ItemAsistenciaBinding
import com.drogavet.asistencias.domain.model.AsistenciaConEmpleado
import com.drogavet.asistencias.domain.model.EstadoAsistencia

class AsistenciaAdapter(
    private var lista: List<AsistenciaConEmpleado>
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

            tvNombre.text = "${asistencia.nombre} ${asistencia.apellido}"
            tvFecha.text = asistencia.fecha
            tvEntrada.text = asistencia.horaEntrada ?: "--:--"
            tvSalida.text = asistencia.horaSalida ?: "--:--"

            btnEstado.text = asistencia.estado.name

            when (asistencia.estado) {

                EstadoAsistencia.A_TIEMPO -> {
                    btnEstado.setBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.green_entrada)
                    )
                }

                EstadoAsistencia.TARDANZA -> {
                    btnEstado.setBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.tile2_usuarios)
                    )
                }

                EstadoAsistencia.FALTA -> {
                    btnEstado.setBackgroundColor(
                        ContextCompat.getColor(root.context, R.color.red_salida)
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int = lista.size

    fun submit(nuevaLista: List<AsistenciaConEmpleado>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}