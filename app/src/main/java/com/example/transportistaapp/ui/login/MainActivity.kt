package com.example.transportistaapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.transportistaapp.R
import com.example.transportistaapp.databinding.ActivityMainBinding
import com.example.transportistaapp.ui.homeTransportista.RutasActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            loginViewModel.login(email, password)
        }
        initUI()
    }

    private fun initUI() {
        initState()
    }

    private fun initState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect { state ->
                    when (state) {
                        LoginState.Idle -> {
                            binding.tvStatus.text = ""
                            binding.tvStatus.visibility = TextView.GONE
                        }
                        LoginState.Loading -> {
                            binding.tvStatus.text = getString(R.string.CARGANDO)
                            binding.tvStatus.visibility = TextView.VISIBLE
                        }
                        is LoginState.Success -> {
                            navigateToDashboard()
                        }
                        is LoginState.Failure -> {
                            binding.tvStatus.text = state.error
                            binding.tvStatus.visibility = TextView.VISIBLE
                        }
                    }
                }
            }
        }
    }
    private fun navigateToDashboard() {
        val intent = Intent(this, RutasActivity::class.java)
        startActivity(intent)
        finish()

    }
}

