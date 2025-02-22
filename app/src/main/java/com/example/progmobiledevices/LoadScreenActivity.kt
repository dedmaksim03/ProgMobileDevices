package com.example.progmobiledevices

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoadScreenActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000 // 3 секунды

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen_activity) // Замените на имя вашего layout файла

        // Используем Handler с Looper.getMainLooper()
        Handler(Looper.getMainLooper()).postDelayed({
            // Создаем Intent для перехода на MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Закрываем SplashScreenActivity, чтобы пользователь не мог вернуться назад
            finish()
        }, SPLASH_DELAY)
    }
}