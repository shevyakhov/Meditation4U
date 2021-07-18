package com.example.meditation4u

import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startingAnimation()

        loginBtn.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        toRegistrationMain.setOnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun startingAnimation() {
        greetStart.animate().alpha(0.0f).setDuration(0)
        greetEnd.animate().alpha(0.0f).setDuration(0)
        loginBtn.animate().alpha(0.0f).setDuration(0)
        toRegistrationMain.animate().alpha(0.0f).setDuration(0)
        ObjectAnimator.ofFloat(icon, "translationY", -1800f).apply {
            duration = 3000
            start()
        }

        android.os.Handler().postDelayed({
            greetStart.animate().alpha(1.0f).setDuration(1000)
            greetEnd.animate().alpha(1.0f).setDuration(1000)
            loginBtn.animate().alpha(1.0f).setDuration(1000)
            toRegistrationMain.animate().alpha(1.0f).setDuration(1000)
        }, 2000)
/*TODO 1)Login btn
*      2)Register btn*/
    }
}