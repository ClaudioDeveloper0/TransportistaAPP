package com.example.transportistaapp.ui.finalizaciondia

import android.view.View
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.transportistaapp.databinding.FragmentFinalizacionDiaBinding
import com.example.transportistaapp.domain.model.Paquete
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FinalizacionDiaFragment : Fragment() {

    private var _binding: FragmentFinalizacionDiaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FinalizacionDiaViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinalizacionDiaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()

        binding.btnFinalizarDia.setOnClickListener {
            viewModel.finalizarDia()
        }
    }

    private fun initUI() {
        initStates()
    }

    private fun initStates() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect {
                        when (it) {
                            is FinalizacionDiaState.Error -> errorState(it.paquetes)
                            FinalizacionDiaState.Loading -> loadingState()
                            is FinalizacionDiaState.Success -> successState()
                        }
                    }
                }
            }
        }
    }
    private fun loadingState() {
        //TODO("Hay que poner un spinner o algo de cargando en la pantalla")
    }
    private fun successState() {
        //Todo("Pendiente...")
    }
    private fun errorState(e:List<Paquete>) {
        Log.d("MaybeaLog", "error, aun hay paquetes: $e")
    }
}
