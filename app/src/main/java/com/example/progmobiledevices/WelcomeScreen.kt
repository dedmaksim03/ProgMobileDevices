package com.example.progmobiledevices

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.progmobiledevices.databinding.ActivityWelcomeScreenBinding
import com.google.android.material.tabs.TabLayoutMediator

class WelcomeScreen : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeScreenBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация ViewPagerAdapter
        viewPagerAdapter = ViewPagerAdapter(this)

        // Add fragments to the adapter
        viewPagerAdapter.addFragment(WelcomeScreen_page1())
        viewPagerAdapter.addFragment(WelcomeScreen_page2())
        viewPagerAdapter.addFragment(WelcomeScreen_page3())

        binding.viewPager.adapter = viewPagerAdapter // Set the adapter

        // Установка слушателя для ViewPager2
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
                if (position == 2){
                    binding.nextButton.text = getString(R.string.welcome_screen_last_button)
                }
                else{
                    binding.nextButton.text = getString(R.string.welcome_screen_next_button)
                }

            }
        })

        // Обработчик кнопки "Далее"
        binding.nextButton.setOnClickListener {
            val currentItem = binding.viewPager.currentItem
            if (currentItem < viewPagerAdapter.itemCount - 1) {
                binding.viewPager.currentItem = currentItem + 1
            } else {

                val sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("firstLaunch", "false")
                editor.apply() // или editor.commit()

                val intent = Intent(this@WelcomeScreen, SelectRegistrationOrLogin::class.java)
                startActivity(intent)
            }
        }

        binding.topText.setOnClickListener {
            val sharedPreferences = getSharedPreferences("Preferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("firstLaunch", "false")
            editor.apply()

            val intent = Intent(this@WelcomeScreen, SelectRegistrationOrLogin::class.java)
            startActivity(intent)
        }

        // Инициализация индикаторов
        updateIndicators(0)

    }

    private fun updateIndicators(position: Int) {
        val indicators = arrayOf(
            binding.root.findViewById<View>(R.id.indicator1)!!,
            binding.root.findViewById<View>(R.id.indicator2)!!,
            binding.root.findViewById<View>(R.id.indicator3)!!
        )

        for (i in indicators.indices) {
            val indicator = indicators[i]
            val layoutParams = indicator.layoutParams as LinearLayout.LayoutParams

            if (i == position) {
                layoutParams.width = 100
                indicator.background = ContextCompat.getDrawable(this, R.drawable.welcome_screen_custom_tab_indicator)
            } else {
                layoutParams.width = 30
                indicator.background = ContextCompat.getDrawable(this, R.drawable.welcome_screen_inactive_tab_indicator)
            }

            indicator.layoutParams = layoutParams
        }
    }
}