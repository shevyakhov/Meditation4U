package com.example.meditation4u

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        toLoginBtn.setOnClickListener{
            intent = Intent(this, LoginActivity::class.java)
            /*intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP*/
            startActivity(intent)
            finish()
        }
    }
}