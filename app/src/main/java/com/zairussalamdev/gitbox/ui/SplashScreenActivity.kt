package com.zairussalamdev.gitbox.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.zairussalamdev.gitbox.R
import com.zairussalamdev.gitbox.ui.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    companion object {
        const val SPLASH_SCREEN_DELAY_MILS = 2500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_SCREEN_DELAY_MILS)
    }
}