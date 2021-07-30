@file:Suppress("DEPRECATION")

package com.example.meditation4u.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation4u.R
import kotlinx.android.synthetic.main.activity_main.*
import maes.tech.intentanim.CustomIntent

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startingAnimation()
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
        greetStart.animate().alpha(0.0f).setDuration(0)
        greetEnd.animate().alpha(0.0f).setDuration(0)
        loginBtn.animate().alpha(0.0f).setDuration(0)
        toRegistrationMain.animate().alpha(0.0f).setDuration(0)

        android.os.Handler().postDelayed({
            greetStart.animate().alpha(1.0f).setDuration(1000)
            greetEnd.animate().alpha(1.0f).setDuration(1000)
            loginBtn.animate().alpha(1.0f).setDuration(1000)
            toRegistrationMain.animate().alpha(1.0f).setDuration(1000)

        }, 500)

    }
}