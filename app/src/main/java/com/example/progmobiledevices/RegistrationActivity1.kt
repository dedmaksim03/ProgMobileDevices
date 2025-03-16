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
import com.example.progmobiledevices.databinding.ActivityRegistration1Binding

class RegistrationActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityRegistration1Binding // Объявляем Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityRegistration1Binding.inflate(layoutInflater) // Инициализируем Binding
        setContentView(binding.root)

        binding.buttonNext.setOnClickListener { // Используем Binding для доступа к кнопке
            val intent = Intent(this, RegistrationActivity2::class.java)
            startActivity(intent)
            finish()
        }

        binding.arrowBack.setOnClickListener {
            val intent = Intent(this, SelectRegistrationOrLogin::class.java)
            startActivity(intent)
            finish()
        }

        var isEmailValid = false
        var isPasswordValid = false
        var isRetryPasswordValid = false

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
                binding.buttonNext.isEnabled = isEmailValid && isPasswordValid && isRetryPasswordValid && binding.checkboxApprove.isChecked
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
                binding.buttonNext.isEnabled = isEmailValid && isPasswordValid && isRetryPasswordValid && binding.checkboxApprove.isChecked
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.passwordRetryInnerTextField.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                isRetryPasswordValid = binding.passwordRetryInnerTextField.text.toString() == binding.passwordInnerTextField.text.toString()

                if (!isRetryPasswordValid) {
                    binding.passwordTextField.error = "Пароли должны совпадать"
                    binding.passwordRetryTextField.error = "Пароли должны совпадать"
                } else {
                    binding.passwordTextField.error = null
                    binding.passwordTextField.isErrorEnabled = false
                    binding.passwordRetryTextField.error = null
                    binding.passwordRetryTextField.isErrorEnabled = false
                }

                // Включаем кнопку, только если оба поля заполнены и валидны
                binding.buttonNext.isEnabled = isEmailValid && isPasswordValid && isRetryPasswordValid && binding.checkboxApprove.isChecked
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.checkboxApprove.setOnClickListener {
            binding.buttonNext.isEnabled = isEmailValid && isPasswordValid && isRetryPasswordValid && binding.checkboxApprove.isChecked
        }

    }
}
