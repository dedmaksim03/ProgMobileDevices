package com.example.progmobiledevices

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import androidx.core.view.isNotEmpty
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
            val intent = Intent(this, Registration_activity_1::class.java)
            startActivity(intent)
            finish()
        }

//        binding.buttonLogin.isEnabled = false
//
//        val textWatcher = object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                // Проверяем, заполнены ли оба поля
//                binding.buttonLogin.isEnabled = !binding.emailInnerTextField.text.isNullOrEmpty() && !binding.passwordInnerTextField.text.isNullOrEmpty()
//            }
//
//            override fun afterTextChanged(s: Editable?) {}
//        }
//
//        binding.emailInnerTextField.addTextChangedListener(textWatcher)
//        binding.passwordInnerTextField.addTextChangedListener(textWatcher)

    }
}