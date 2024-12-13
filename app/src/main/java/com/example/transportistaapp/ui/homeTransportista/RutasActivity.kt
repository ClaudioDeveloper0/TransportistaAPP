package com.example.transportistaapp.ui.homeTransportista

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint
import com.example.transportistaapp.databinding.ActivityRutasBinding
import com.example.transportistaapp.ui.homeTransportista.fragments.verRutas.VerRutasFragment
@AndroidEntryPoint
class RutasActivity : AppCompatActivity() {
    lateinit var binding: ActivityRutasBinding

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
