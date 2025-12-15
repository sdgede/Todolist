package com.example.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.todolist.R
import com.example.todolist.adapter.OnboardingAdapter
import com.example.todolist.model.OnboardingItem
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var btnNext: Button
    private lateinit var btnSkip: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        btnNext = findViewById(R.id.btnNext)
        btnSkip = findViewById(R.id.btnSkip)

        val items = listOf(
            OnboardingItem(
                R.drawable.ic_check,
                "Organize Tasks",
                "All your tasks in one place."
            ),
            OnboardingItem(
                R.drawable.ic_calendar,
                "Set Deadlines",
                "Never miss important dates."
            ),
            OnboardingItem(
                R.drawable.ic_bell,
                "Stay Productive",
                "Smart reminders help you focus."
            )
        )

        viewPager.adapter = OnboardingAdapter(items)
        TabLayoutMediator(findViewById(R.id.dots), viewPager) { _, _ -> }.attach()

        btnNext.setOnClickListener {
            if (viewPager.currentItem < items.size - 1) {
                viewPager.currentItem++
            } else {
                finishOnboarding()
            }
        }

        btnSkip.setOnClickListener {
            finishOnboarding()
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                btnNext.text =
                    if (position == items.size - 1) "Get Started" else "Next"
            }
        })
    }

    private fun finishOnboarding() {
        getSharedPreferences("APP_PREF", MODE_PRIVATE)
            .edit()
            .putBoolean("ONBOARDING_DONE", true)
            .apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
