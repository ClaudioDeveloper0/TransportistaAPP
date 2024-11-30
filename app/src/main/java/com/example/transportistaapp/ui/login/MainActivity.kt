package com.example.transportistaapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.transportistaapp.R
import com.example.transportistaapp.ui.homeTransportista.DashboardActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val statusTextView = findViewById<TextView>(R.id.statusTextView)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            loginViewModel.login(email, password)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                loginViewModel.loginState.collect { state ->
                    when (state) {
                        LoginState.Idle -> {
                            statusTextView.text = ""
                            statusTextView.visibility = TextView.GONE
                        }
                        LoginState.Loading -> {
                            statusTextView.text = getString(R.string.CARGANDO)
                            statusTextView.visibility = TextView.VISIBLE
                        }
                        is LoginState.Success -> {
                            navigateToDashboard()
                        }
                        is LoginState.Failure -> {
                            statusTextView.text = state.error
                            statusTextView.visibility = TextView.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}

