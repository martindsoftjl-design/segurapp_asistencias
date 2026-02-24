package com.drogavet.asistencias.ui.empleado

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drogavet.asistencias.data.local.entity.EmpleadoEntity
import com.drogavet.asistencias.databinding.ItemEmpleadoBinding

class EmpleadoAdapter(
    private var lista: List<EmpleadoEntity>
) : RecyclerView.Adapter<EmpleadoAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemEmpleadoBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEmpleadoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val empleado = lista[position]

        holder.binding.tvNombre.text = empleado.nombre
        holder.binding.tvDni.text = "DNI: ${empleado.dni}"
        holder.binding.tvCargo.text = empleado.cargo
    }

    fun actualizarLista(nuevaLista: List<EmpleadoEntity>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}