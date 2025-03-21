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

        loadFragment(HomePageFragment())

        val searchFragment = supportFragmentManager.findFragmentById(R.id.footer) as MainFooter
//        val settingsFragment = supportFragmentManager.findFragmentById(R.id.homePage) as MainSettingsFragment

        // Устанавливаем слушатель
        searchFragment.setOnFooterClickListener(this)
//        settingsFragment.setOnSettingsClickListener(this)

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

        loadFragment(newFragment)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Контейнер для фрагментов
            .addToBackStack(null)
            .commit()
    }
}