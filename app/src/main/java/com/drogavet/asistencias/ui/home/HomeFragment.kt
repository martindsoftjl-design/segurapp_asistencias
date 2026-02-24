package com.drogavet.asistencias.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.drogavet.asistencias.R
import com.drogavet.asistencias.data.local.SessionManager
import com.drogavet.asistencias.databinding.FragmentHomeBinding
import android.content.Intent
import com.drogavet.asistencias.ui.login.LoginActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)

        val usuario = SessionManager.usuarioActual

        if (usuario != null) {

            binding.tvNombreUsuario.text = usuario.nombre
            binding.tvRolUsuario.text = usuario.rol

            // Cambiar icono según rol
            if (usuario.rol == "ADMIN") {
                binding.imgRol.setImageResource(R.drawable.ic_admin) // crea un icono si quieres
                binding.tvRolUsuario.setBackgroundColor(
                    requireContext().getColor(R.color.md_theme_primary)
                )
            } else {
                binding.imgRol.setImageResource(R.drawable.ic_person)
                binding.tvRolUsuario.setBackgroundColor(
                    requireContext().getColor(R.color.green_entrada)
                )
            }
        }
        binding.btnCerrarSesion.setOnClickListener {
            SessionManager.usuarioActual = null
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        // tus cards siguen igual
        binding.cardRegistrar.setOnClickListener {
            findNavController().navigate(R.id.registrarFragment)
        }

        binding.cardVer.setOnClickListener {
            findNavController().navigate(R.id.verFragment)
        }

        binding.cardReportes.setOnClickListener {
            findNavController().navigate(R.id.reportesFragment)
        }

        binding.cardUsuarios.setOnClickListener {
            findNavController().navigate(R.id.usuariosFragment)
        }
        binding.cardEmpleados.setOnClickListener {

            requireActivity()
                .findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNav)
                .visibility = View.GONE

            findNavController().navigate(R.id.registrarEmpleadoFragment)
        }

        binding.cardVerEmpleados.setOnClickListener {

            requireActivity()
                .findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottomNav)
                .visibility = View.GONE

            findNavController().navigate(R.id.verEmpleadosFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}