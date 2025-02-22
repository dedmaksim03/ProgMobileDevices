package com.example.progmobiledevices

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.progmobiledevices.databinding.ActivityWelcomeScreenBinding
import com.google.android.material.tabs.TabLayoutMediator

class WelcomeScreen : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeScreenBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter // Corrected type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация ViewPagerAdapter
        viewPagerAdapter = ViewPagerAdapter(this) // Pass the FragmentActivity

        // Add fragments to the adapter
        viewPagerAdapter.addFragment(WelcomeScreen_page1())
        viewPagerAdapter.addFragment(WelcomeScreen_page2())
        viewPagerAdapter.addFragment(WelcomeScreen_page3())

        binding.viewPager.adapter = viewPagerAdapter // Set the adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // tab.text = "Screen ${position + 1}" // Убираем текст
        }.attach()

        // Установите количество вкладок
        val tabCount = 3 // Или adapter.itemCount
        for (i in 0 until tabCount) {
            binding.tabLayout.getTabAt(i)?.let { tab ->
                tab.view.isClickable = true // Сделайте вкладки кликабельными
            }
        }
    }
}