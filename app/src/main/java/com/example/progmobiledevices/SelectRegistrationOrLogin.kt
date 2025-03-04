package com.example.progmobiledevices

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.progmobiledevices.databinding.ActivityNoInternetConnectionBinding
import com.example.progmobiledevices.databinding.ActivitySelectRegistrationOrLoginBinding

class SelectRegistrationOrLogin : AppCompatActivity() {

    private lateinit var binding: ActivitySelectRegistrationOrLoginBinding // Объявляем Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_select_registration_or_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivitySelectRegistrationOrLoginBinding.inflate(layoutInflater) // Инициализируем Binding
        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener { // Используем Binding для доступа к кнопке
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonRegistration.setOnClickListener { // Используем Binding для доступа к кнопке
            val intent = Intent(this, Registration_activity_1::class.java)
            startActivity(intent)
            finish()
        }
    }
}