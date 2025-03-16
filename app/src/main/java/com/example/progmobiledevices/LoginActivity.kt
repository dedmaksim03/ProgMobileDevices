package com.example.progmobiledevices

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.progmobiledevices.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrationView.setOnClickListener {
            val intent = Intent(this, RegistrationActivity1::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonLogin.isEnabled = false

        var isPasswordValid = false
        var isEmailValid = false

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = binding.emailInnerTextField.text.toString()
                val password = binding.passwordInnerTextField.text.toString()

                val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                val isPasswordValid = password.length > 8

                // Отображаем ошибку, если email невалидный
                if (!isEmailValid) {
                    binding.emailTextField.error = "Некорректный формат электронной почты"
                } else {
                    binding.emailTextField.error = null // Очищаем ошибку, если email валиден
                    binding.emailTextField.isErrorEnabled = false
                }

                if (!isPasswordValid) {
                    binding.passwordTextField.error = "Пароль должен быть более 8 символов"
                } else {
                    binding.passwordTextField.error = null
                    binding.passwordTextField.isErrorEnabled = false
                }

                // Включаем кнопку, только если оба поля заполнены и валидны
                binding.buttonLogin.isEnabled = isEmailValid && isPasswordValid
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        binding.emailInnerTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = binding.emailInnerTextField.text.toString()

                isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()

                // Отображаем ошибку, если email невалидный
                if (!isEmailValid) {
                    binding.emailTextField.error = "Некорректный формат электронной почты"
                } else {
                    binding.emailTextField.error = null // Очищаем ошибку, если email валиден
                    binding.emailTextField.isErrorEnabled = false
                }

                // Включаем кнопку, только если оба поля заполнены и валидны
                binding.buttonLogin.isEnabled = isEmailValid && isPasswordValid
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.passwordInnerTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val password = binding.passwordInnerTextField.text.toString()

                isPasswordValid = password.length > 8

                if (!isPasswordValid) {
                    binding.passwordTextField.error = "Пароль должен быть более 8 символов"
                } else {
                    binding.passwordTextField.error = null
                    binding.passwordTextField.isErrorEnabled = false
                }

                // Включаем кнопку, только если оба поля заполнены и валидны
                binding.buttonLogin.isEnabled = isEmailValid && isPasswordValid
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }
}