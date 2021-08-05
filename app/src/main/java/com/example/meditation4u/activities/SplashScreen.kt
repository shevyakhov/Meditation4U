package com.example.meditation4u.activities

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation4u.R
import com.example.meditation4u.constants.*
import kotlinx.android.synthetic.main.activity_splash_screen.*
import maes.tech.intentanim.CustomIntent

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var id: String
    private lateinit var email: String
    private lateinit var nickName: String
    private lateinit var avatar: String
    private lateinit var token: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        getSavedData()

        startingAnimation()
        intentHandler()
    }

    private fun startingAnimation() {
        splashIcon.animate().alpha(0.0f).duration = 0

        android.os.Handler().postDelayed({
            splashIcon.animate().alpha(1.0f).duration = 1000
        }, 500)
    }

    private fun getSavedData() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)

        id = sharedPreferences.getString(ID, "").toString()
        email = sharedPreferences.getString(EMAIL, "").toString()
        nickName = sharedPreferences.getString(NICKNAME, "").toString()
        avatar = sharedPreferences.getString(AVATAR, "").toString()
        token = sharedPreferences.getString(TOKEN, "").toString()
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

    private fun intentHandler() {
        android.os.Handler().postDelayed({
            if (email == EMPTY || id == EMPTY) {
                intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                this.overridePendingTransition(0, 0)
                finish()
            } else {

                intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra(ID, id)
                intent.putExtra(EMAIL, email)
                intent.putExtra(NICKNAME, nickName)
                intent.putExtra(AVATAR, avatar)
                intent.putExtra(TOKEN, token)
                startActivity(intent)
                CustomIntent.customType(this, "fadein-to-fadeout")
                finish()
            }


        }, 2000)
    }
}