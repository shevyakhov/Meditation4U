package com.example.meditation4u.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.meditation4u.R
import com.example.meditation4u.UserApi.LoginResponse
import com.example.meditation4u.UserApi.UserApi
import com.example.meditation4u.UserApi.UserRequest
import com.google.android.gms.common.api.Api
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
import maes.tech.intentanim.CustomIntent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    lateinit var userApi: UserApi
    var userLogged: LoginResponse? = null
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configureRetrofit()
        signInBtn.setOnClickListener {
            onLogin()
            android.os.Handler().postDelayed({
                if (userLogged != null) {
                    vibratePhone()
                    intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("id", userLogged!!.id)
                    intent.putExtra("email", userLogged!!.email)
                    intent.putExtra("nickName", userLogged!!.nickName)
                    intent.putExtra("avatar", userLogged!!.avatar)
                    intent.putExtra("token", userLogged!!.token)
                    startActivity(intent)
                    CustomIntent.customType(this, "fadein-to-fadeout")
                    finish()
                } else
                    Toast.makeText(this, "Невозможно войти", Toast.LENGTH_SHORT).show()

            }, 500)
        }
        toRegistration.setOnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "fadein-to-fadeout")
        }
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
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) { // Vibrator availability checking
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        100,
                        5
                    )
                ) // New vibrate method for API Level 26 or higher
            } else {
                vibrator.vibrate(100) // Vibrate method for below API Level 26
            }
        }
    }
}