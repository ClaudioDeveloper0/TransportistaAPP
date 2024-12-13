package com.example.transportistaapp.ui.cargaentregas

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoCargaEntregasFragment : Fragment(R.layout.fragment_listado_carga_entregas) {

    private lateinit var binding: Fragment_listado
    private val viewModel: ListadoCargaEntregasViewModel by viewModels()
    private lateinit var adapter: CajaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = Fragment_Listado.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@ListadoCargaEntregasFragment.viewModel
        }

        // Inicializar el RecyclerView con un adaptador
        adapter = CajasAdapter()
        binding.recyclerViewCajas.adapter = adapter

        // Observar el estado de las cajas
        viewModel.cajasLiveData.observe(viewLifecycleOwner) { cajas ->
            adapter.submitList(cajas)
        }

        binding.btnEscanearCaja.setOnClickListener {
            // Lógica para registrar entrega
            val cajasAEntregar = adapter.getSelectedCajas()
            viewModel.registrarEntregas(cajasAEntregar)
        }
    }
}
