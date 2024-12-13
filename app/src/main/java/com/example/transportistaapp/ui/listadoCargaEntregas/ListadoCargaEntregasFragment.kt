package com.example.transportistaapp.ui.listadoCargaEntregas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transportistaapp.databinding.FragmentListadoBinding
import com.example.transportistaapp.ui.listadoCargaEntregas.adapter.ListadoCajasEntregasAdapter
import com.example.transportistaapp.ui.homeTransportista.fragments.verRutas.adapter.VerRutasAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoCargaEntregasFragment : Fragment() {

    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListadoCargaEntregasViewModel by viewModels()
    private lateinit var adapter: ListadoCajasEntregasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListadoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initAdapter()
    }

    private fun initAdapter() {
        private fun initAdapter() {
            verRutasAdapter = VerRutasAdapter()
            binding.rvPaquetes.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = verRutasAdapter
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
