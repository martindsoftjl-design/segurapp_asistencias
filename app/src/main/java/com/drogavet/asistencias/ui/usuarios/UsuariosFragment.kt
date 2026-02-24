package com.drogavet.asistencias.ui.usuarios

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.drogavet.asistencias.databinding.FragmentUsuariosBinding
import com.drogavet.asistencias.domain.model.Usuario

class UsuariosFragment : Fragment() {

    private var _binding: FragmentUsuariosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsuariosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val usuariosDemo = listOf(
            Usuario(1, "Luis Miguel Palomino", "Administrador",
                "luis.palomino.12@gmail.com", "+51 963 259 652"),

            Usuario(2, "Maria Antonieta Pérez", "RR.HH",
                "mariaantonieta3@gmail.com", "+51 911 256 445"),

            Usuario(3, "Juan Carlos Mendez", "Seguridad",
                "juancamino145@gmail.com", "+51 999 565 458")
        )

        val adapter = UsuariosAdapter(usuariosDemo)

        binding.rvUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUsuarios.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}