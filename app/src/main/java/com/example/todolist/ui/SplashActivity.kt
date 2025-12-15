package com.example.todolist.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.todolist.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            val prefs = getSharedPreferences("APP_PREF", MODE_PRIVATE)
            val onboardingDone = prefs.getBoolean("ONBOARDING_DONE", false)

            val intent = if (onboardingDone) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, OnboardingActivity::class.java)
            }

            startActivity(intent)
            finish()
        }, 1500)
    }
}
