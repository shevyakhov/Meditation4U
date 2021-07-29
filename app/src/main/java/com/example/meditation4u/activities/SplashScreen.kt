package com.example.meditation4u.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation4u.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        startingAnimation()
    }
    private fun startingAnimation() {
        ObjectAnimator.ofFloat(splashIcon, "translationY", -1800f).apply {
            duration = 2000
            start()
        }
        android.os.Handler().postDelayed({
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}