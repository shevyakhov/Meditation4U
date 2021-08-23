package com.example.meditation4u.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation4u.R
import com.example.meditation4u.UserApi.LoginResponse
import com.example.meditation4u.UserApi.UserApi
import com.example.meditation4u.UserApi.UserRequest
import com.example.meditation4u.constants.*
import kotlinx.android.synthetic.main.activity_login.*
import maes.tech.intentanim.CustomIntent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class LoginActivity : AppCompatActivity() {
    private lateinit var userApi: UserApi
    var userLogged: LoginResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        configureRetrofit()

        loadEditTextData()

        setListeners()
    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mskko2021.mad.hakta.pro/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        userApi = retrofit.create(UserApi::class.java)
    }

    private fun loadEditTextData() {
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        signInEmail.text.append(sharedPreferences.getString(EMAIL, ""))
    }

    private fun setListeners() {

        toRegistration.setOnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "fadein-to-fadeout")
        }

        signInBtn.setOnClickListener {
            onLogin()
            android.os.Handler().postDelayed({
                if (userLogged != null) {
                    vibratePhone()
                    saveData(userLogged!!)
                    intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra(ID, userLogged!!.id)
                    intent.putExtra(EMAIL, userLogged!!.email)
                    intent.putExtra(NICKNAME, userLogged!!.nickName)
                    intent.putExtra(AVATAR, userLogged!!.avatar)
                    intent.putExtra(TOKEN, userLogged!!.token)
                    startActivity(intent)
                    CustomIntent.customType(this, "fadein-to-fadeout")
                    finish()
                } else
                    Toast.makeText(this, "Невозможно войти", Toast.LENGTH_SHORT).show()

            }, 500)
        }
    }
    private fun onLogin() {
        val user = UserRequest(
            signInEmail.text.toString().trim(),
            signInPassword.text.toString().trim()
        )
        userApi.getLogin(user)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Log.e("response", response.toString())
                    userLogged = response.body()
                    Log.e("!!!!!!", userLogged.toString())
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("failure", "failure", t)
                }

            })
    }

    private fun vibratePhone() {
        /* vibrate if logged */
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        100,
                        5
                    )
                )
            } else {
                vibrator.vibrate(100)
            }
        }
    }

    private fun saveData(user: LoginResponse) {
        val sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(ID, user.id)
        editor.putString(EMAIL, user.email)
        editor.putString(NICKNAME, user.nickName)
        editor.putString(AVATAR, user.avatar)
        editor.putString(TOKEN, user.token)
        editor.apply()
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