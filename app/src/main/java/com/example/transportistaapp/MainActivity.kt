package com.example.transportistaapp


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import com.example.transportistaapp.R
import com.example.transportistaapp.viewmodel.AuthViewModel
import com.example.transportistaapp.viewmodel.LoginState
import com.example.transportistaapp.ui.DashboardActivity
import kotlinx.coroutines.flow.collect
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

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
                statusTextView.text = "Por favor, completa todos los campos."
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
                            statusTextView.text = "Cargando..."
                            statusTextView.visibility = TextView.VISIBLE
                        }
                        is LoginState.Success -> {
                            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is LoginState.Failure -> {
                            statusTextView.text = "Error: ${state.error}"
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
