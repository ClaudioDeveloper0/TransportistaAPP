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
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val emailEditText = findViewById<EditText>(R.id.email)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.login_button)
        val statusTextView = findViewById<TextView>(R.id.statusTextView)

        // Validar que los campos no estén vacíos
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isBlank() || password.isBlank()) {
                statusTextView.text = getString(R.string.COMPLETAR_TODOS_LOS_CAMPOS)
                statusTextView.visibility = TextView.VISIBLE
                return@setOnClickListener
            }

            authViewModel.login(email, password)
        }

        // Usar repeatOnLifecycle para recoger los flujos de estado de LoginState
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.loginState.collect { state ->
                    when (state) {
                        is LoginState.Loading -> {
                            statusTextView.text = getString(R.string.CARGANDO)
                            statusTextView.visibility = TextView.VISIBLE
                        }
                        is LoginState.Success -> {
                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is LoginState.Failure -> {
                            statusTextView.text = getString(R.string.ERROR, state.error)
                            statusTextView.visibility = TextView.VISIBLE
                        }
                        LoginState.Idle -> {
                            statusTextView.text = ""
                            statusTextView.visibility = TextView.GONE
                        }
                    }
                }
            }
        }

    }
}
