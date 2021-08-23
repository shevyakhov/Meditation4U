@file:Suppress("DEPRECATION")

package com.example.meditation4u.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation4u.R
import kotlinx.android.synthetic.main.activity_main.*
import maes.tech.intentanim.CustomIntent

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        startingAnimation()
        setButtonListeners()

    }


    private fun setButtonListeners() {
        loginBtn.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "fadein-to-fadeout")
            finish()
        }
        toRegistrationMain.setOnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "fadein-to-fadeout")
            finish()
        }
    }

    private fun startingAnimation() {
        greetStart.animate().alpha(0.0f).duration = 0
        greetEnd.animate().alpha(0.0f).duration = 0
        loginBtn.animate().alpha(0.0f).duration = 0
        icon.animate().alpha(0.0f).duration = 0

        toRegistrationMain.animate().alpha(0.0f).duration = 0

        android.os.Handler().postDelayed({
            greetStart.animate().alpha(1.0f).duration = 500
            greetEnd.animate().alpha(1.0f).duration = 500
            loginBtn.animate().alpha(1.0f).duration = 500
            toRegistrationMain.animate().alpha(1.0f).duration = 500
            icon.animate().alpha(1.0f).duration = 500

        }, 200)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

}