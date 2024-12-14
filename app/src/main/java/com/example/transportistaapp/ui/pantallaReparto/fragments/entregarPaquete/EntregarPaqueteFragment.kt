package com.example.transportistaapp.ui.pantallaReparto.fragments.entregarPaquete

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.transportistaapp.R
import com.example.transportistaapp.databinding.FragmentEntregarPaqueteBinding
import com.example.transportistaapp.domain.model.Paquete
import com.example.transportistaapp.ui.pantallaReparto.fragments.BarcodeScannerFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntregarPaqueteFragment : Fragment() {
    private val viewModel: EntregarPaqueteViewModel by viewModels()
    private var _binding: FragmentEntregarPaqueteBinding? = null
    private val binding get() = _binding!!
    private lateinit var paquete: Paquete

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntregarPaqueteBinding.inflate(layoutInflater, container, false)
        viewModel.obtenerPaquete(arguments?.getString("codigoPaquete") ?: "")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        initListeners()
        initUIState()
    }

    private fun initUIState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.state.collect {
                        when (it) {
                            EntregarPaqueteState.Loading -> loadingState()
                            is EntregarPaqueteState.Success -> successState(it.paquete)
                            is EntregarPaqueteState.Error -> errorState(it.error)
                        }
                    }
                }
            }
        }
    }

    private fun loadingState() {
        //TODO("Hay que poner un spinner o algo de cargando en la pantalla")
    }

    private fun successState(p: Paquete) {
        paquete = p
        binding.tvPaqueteCodigo.text = p.id
        binding.tvRecibeValue.text = p.receptor
        binding.tvTelefonoValue.text = p.contacto
    }

    private fun errorState(error: String) {
        Log.d("MaybeaLog", error)
    }

    private fun initListeners() {
        binding.btnValidar.setOnClickListener {
            val barcodeFragment = BarcodeScannerFragment().apply {
                arguments = Bundle().apply {
                    putString("successCode", paquete.id)
                }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, barcodeFragment)
                .addToBackStack(null)
                .commit()
        }
        parentFragmentManager.setFragmentResultListener("BARCODE_SCAN", this) { _, bundle ->
            val barcodeResult = bundle.getString("BARCODE_RESULT")
            if (barcodeResult != null) {
                if (barcodeResult == paquete.id) {
                    binding.seccionValidada.visibility = View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Código Validado: $barcodeResult",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    binding.seccionValidada.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Codigo incorrecto: $barcodeResult",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        binding.checkboxReceptor.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.editTextNombre.setText(paquete.receptor)
                binding.editTextNombre.visibility = View.GONE
                binding.textViewNombre.visibility = View.GONE
                binding.editTextTelefono.visibility = View.GONE
                binding.textViewTelefono.visibility = View.GONE
            } else {
                binding.editTextNombre.setText("")
                binding.editTextNombre.visibility = View.VISIBLE
                binding.textViewNombre.visibility = View.VISIBLE
                binding.editTextTelefono.visibility = View.VISIBLE
                binding.textViewTelefono.visibility = View.VISIBLE
            }
        }
        binding.btnConfirmarEntrega.setOnClickListener {
            viewModel.registrarEntrega(
                binding.editTextNombre.text.toString(),
                binding.editTextRut.text.toString(),
                binding.editTextTelefono.toString(),
                paquete.id
            )
        }
    }
}
