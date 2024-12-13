package com.example.transportistaapp.ui.finalizaciondia

import android.view.View
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FinalizacionDiaFragment : Fragment(R.layout.fragment_finalizacion_dia) {

    private lateinit var binding: FragmentFinalizacionDiaBinding
    private val viewModel: FinalizacionDiaViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFinalizacionDiaBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@FinalizacionDiaFragment.viewModel
        }

        // Observar el estado de las entregas no realizadas
        viewModel.entregasNoRealizadasLiveData.observe(viewLifecycleOwner) { entregas ->
            // Mostrar las entregas que no se realizaron y permitir registrar justificación o reintentar

        }

        binding.btnFinalizarDia.setOnClickListener {
            viewModel.finalizarDia()
        }
    }
}
