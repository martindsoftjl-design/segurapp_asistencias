package com.drogavet.asistencias.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.drogavet.asistencias.R
import com.drogavet.asistencias.data.local.DatabaseProvider
import com.drogavet.asistencias.data.local.SessionManager
import com.drogavet.asistencias.data.local.entity.UsuarioEntity
import com.drogavet.asistencias.databinding.FragmentLoginBinding
import com.drogavet.asistencias.ui.main.MainActivity
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentLoginBinding.bind(view)

        val db = DatabaseProvider.getDatabase(requireContext())
        val usuarioDao = db.usuarioDao()

        binding.btnLogin.setOnClickListener {

            val email = binding.etUser.text.toString().trim()
            val password = binding.etPass.text.toString().trim()

            if (email.isEmpty()) {
                binding.etUser.error = "Ingrese su email"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etPass.error = "Ingrese su contraseña"
                return@setOnClickListener
            }

            viewLifecycleOwner.lifecycleScope.launch {

                if (usuarioDao.contarUsuarios() == 0) {

                    val admin = UsuarioEntity(
                        nombre = "Administrador",
                        rol = "ADMIN",
                        email = "admin@segurapp.com",
                        telefono = "999999999",
                        password = "1234"
                    )

                    usuarioDao.insertar(admin)
                }

                val usuario = usuarioDao.login(email, password)

                if (usuario != null) {

                    SessionManager.usuarioActual = usuario

                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()

                } else {
                    binding.etPass.error = "Credenciales incorrectas"
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}