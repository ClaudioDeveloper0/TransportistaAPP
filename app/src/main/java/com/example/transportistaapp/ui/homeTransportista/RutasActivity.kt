package com.example.transportistaapp.ui.homeTransportista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.transportistaapp.ui.pantallaReparto.RepartoActivity
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.transportistaapp.R
import com.example.transportistaapp.databinding.ActivityRepartoBinding
import com.example.transportistaapp.databinding.ActivityRutasBinding
import com.example.transportistaapp.domain.model.Ruta
import com.example.transportistaapp.ui.homeTransportista.adapter.RutasAdapter
import com.example.transportistaapp.ui.homeTransportista.fragments.verRutas.VerRutasFragment
import com.example.transportistaapp.ui.pantallaReparto.fragments.listado.ListadoFragment
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RutasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRutasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRutasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<VerRutasFragment>(binding.fragmentContainer.id)
            }
        }
    }



}
