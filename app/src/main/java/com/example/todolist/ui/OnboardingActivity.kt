package com.example.todolist.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.todolist.R
import com.example.todolist.adapter.OnboardingAdapter
import com.example.todolist.model.OnboardingItem
import com.google.android.material.button.MaterialButton
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var dots: DotsIndicator
    private lateinit var btnNext: MaterialButton
    private lateinit var btnSkip: MaterialButton

    private lateinit var items: List<OnboardingItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewPager = findViewById(R.id.viewPager)
        dots = findViewById(R.id.dots)
        btnNext = findViewById(R.id.btnNext)
        btnSkip = findViewById(R.id.btnSkip)

        items = listOf(
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
        dots.attachTo(viewPager)

        // ✅ Listener SEKALI SAJA
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

        // Update UI berdasarkan page
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                val isLastPage = position == items.size - 1

                btnNext.text = if (isLastPage) "Get Started" else "Next"
                btnSkip.visibility = if (isLastPage) ViewPager2.GONE else ViewPager2.VISIBLE
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

