package com.drogavet.asistencias.ui.usuarios

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drogavet.asistencias.databinding.ItemUsuarioBinding
import com.drogavet.asistencias.domain.model.Usuario

class UsuariosAdapter(
    private val lista: List<Usuario>
) : RecyclerView.Adapter<UsuariosAdapter.UsuarioViewHolder>() {

    inner class UsuarioViewHolder(val binding: ItemUsuarioBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = lista[position]

        holder.binding.tvNombreUsuario.text = usuario.nombre
        holder.binding.tvRolUsuario.text = usuario.rol
        holder.binding.tvEmailUsuario.text = usuario.email
        holder.binding.tvTelefonoUsuario.text = usuario.telefono
    }

    override fun getItemCount() = lista.size
}