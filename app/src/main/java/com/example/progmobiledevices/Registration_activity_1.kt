package com.example.progmobiledevices

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.progmobiledevices.databinding.ActivityNoInternetConnectionBinding
import com.example.progmobiledevices.databinding.ActivityRegistration1Binding

class Registration_activity_1 : AppCompatActivity() {
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
            val intent = Intent(this, Registration_activity_2::class.java)
            startActivity(intent)
            finish()
        }

        binding.arrowBack.setOnClickListener {
            val intent = Intent(this, SelectRegistrationOrLogin::class.java)
            startActivity(intent)
            finish()
        }

    }
}
