package com.example.meditation4u

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        toRegistration.setOnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            /*intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP*/
            startActivity(intent)
            finish()
        }
    }
}