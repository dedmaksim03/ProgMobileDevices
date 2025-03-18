package com.example.progmobiledevices

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class MainActivity : AppCompatActivity(), MainFooter.OnFooterClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val searchFragment = supportFragmentManager.findFragmentById(R.id.footer) as MainFooter

        // Устанавливаем слушатель
        searchFragment.setOnFooterClickListener(this)

//        supportFragmentManager.beginTransaction()
//            .add(R.id.main, HomePageFragment())
//            .commit()
    }

    // Реализация метода интерфейса
    override fun onFooterClicked(page: String) {
        var newFragment: Fragment = HomePageFragment()

        when (page){
            "settings" -> newFragment = MainSettingsFragment()
            "home" -> newFragment = HomePageFragment()
        }

        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.homePage, newFragment)
        transaction.addToBackStack(null) // Добавляем в BackStack для возможности возврата
        transaction.commit()
    }
}