package com.example.progmobiledevices

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.progmobiledevices.databinding.ActivityRegistration3Binding
import com.example.progmobiledevices.databinding.ActivityRegistrationFinalBinding

class Registration_activity_final : AppCompatActivity() {
    private lateinit var binding: ActivityRegistrationFinalBinding // Объявляем Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registration1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityRegistrationFinalBinding.inflate(layoutInflater) // Инициализируем Binding
        setContentView(binding.root)

//        binding.buttonNext.setOnClickListener { // Используем Binding для доступа к кнопке
//            val intent = Intent(this, Registration_activity_2::class.java)
//            startActivity(intent)
//            finish() // Закрываем NoInternetActivity
//        }
    }
}