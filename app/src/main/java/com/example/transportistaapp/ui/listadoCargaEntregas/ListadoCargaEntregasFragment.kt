package com.example.transportistaapp.ui.listadoCargaEntregas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transportistaapp.databinding.FragmentListadoBinding
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.ui.listadoCargaEntregas.adapter.ListadoCargaEntregasAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListadoCargaEntregasFragment : Fragment() {

    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListadoCargaEntregasViewModel by viewModels()
    private lateinit var adapter: ListadoCargaEntregasAdapter

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
        initStates()
        initListeners()
    }

    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.paquetesState.collect {
                        when (it) {
                            is ListadoCargaEntregasState.Error -> errorState(it.error)
                            ListadoCargaEntregasState.Loading -> loadingState()
                            is ListadoCargaEntregasState.Success -> successState(it.paquetes)
                        }
                    }
                }
            }
        }
    }
    private fun loadingState() {
        //TODO("Hay que poner un spinner o algo de cargando en la pantalla")
    }
    private fun successState(paquetes : List<Paquete>) {
        adapter.updateList(paquetes)
    }
    private fun errorState(error:String) {
        Log.d("MaybeaLog", error)
    }
    private fun initListeners() {
        binding.btnEscanearCaja.setOnClickListener {
            val cajasAEntregar = adapter.getSelectedCajas()
            viewModel.registrarEntregas(cajasAEntregar)
        }
    }

    private fun initAdapter() {
        adapter = ListadoCargaEntregasAdapter()
        binding.recyclerViewCajas.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = adapter
        }
    }

}
