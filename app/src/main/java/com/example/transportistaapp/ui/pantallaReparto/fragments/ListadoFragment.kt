package com.example.transportistaapp.ui.pantallaReparto.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.transportistaapp.databinding.FragmentListadoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListadoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var _binding: FragmentListadoBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListadoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    companion object {
        const val ARG_PARAM1 = "arg_param1"

        @JvmStatic
        fun newInstance(param1: String) =
            ListadoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}