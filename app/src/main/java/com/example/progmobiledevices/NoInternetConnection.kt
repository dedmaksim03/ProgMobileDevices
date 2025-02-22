package com.example.progmobiledevices

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.progmobiledevices.databinding.ActivityNoInternetConnectionBinding

class NoInternetConnection : AppCompatActivity() {
    private lateinit var binding: ActivityNoInternetConnectionBinding // Объявляем Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoInternetConnectionBinding.inflate(layoutInflater) // Инициализируем Binding
        setContentView(binding.root)

        binding.noInternetConnectionButtonTryAgain.setOnClickListener { // Используем Binding для доступа к кнопке
            val intent = Intent(this, LoadScreenActivity::class.java)
            startActivity(intent)
            finish() // Закрываем NoInternetActivity
        }
    }
}