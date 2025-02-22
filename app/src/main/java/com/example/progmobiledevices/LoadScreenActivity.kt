package com.example.progmobiledevices

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadScreenActivity : AppCompatActivity() {
    private val SPLASH_DELAY: Long = 3000 // 3 секунды

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_screen_activity)

        // Запускаем корутину для проверки подключения к интернету
        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_DELAY) // Даем экрану загрузки немного времени показаться

            if (hasInternetConnection()) {
                // Есть интернет - переходим на MainActivity
                val intent = Intent(this@LoadScreenActivity, WelcomeScreen::class.java)
                startActivity(intent)
            } else {
                // Нет интернета - переходим на NoInternetActivity
                val intent = Intent(this@LoadScreenActivity, NoInternetConnection::class.java)
                startActivity(intent)
            }

            finish() // Закрываем SplashScreenActivity
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}