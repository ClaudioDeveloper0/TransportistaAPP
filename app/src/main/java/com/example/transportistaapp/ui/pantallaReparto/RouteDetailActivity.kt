package com.example.transportistaapp.ui.pantallaReparto

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transportistaapp.databinding.ActivityRouteDetailBinding
import com.example.transportistaapp.ui.pantallaReparto.fragments.listado.adapter.BoxesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRouteDetailBinding
    private val viewModel: RouteDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val routeId = intent.getStringExtra("ROUTE_ID") ?: return
        viewModel.fetchBoxes(routeId)

        binding.rvBoxes.layoutManager = LinearLayoutManager(this)
        viewModel.paquetes.observe(this) { paquetes ->
            binding.rvBoxes.adapter = BoxesAdapter(paquetes) { box ->
                viewModel.validateBox(box.id)
            }
        }

        binding.btnScanBox.setOnClickListener {
            // Implementar función para escanear código QR y validar la caja.
        }
    }
}
