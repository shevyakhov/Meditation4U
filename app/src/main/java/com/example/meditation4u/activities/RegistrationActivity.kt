package com.example.meditation4u.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.meditation4u.R
import com.example.meditation4u.UserApi.CreateUserResponse
import com.example.meditation4u.UserApi.UserApi
import com.example.meditation4u.UserApi.UserRequest
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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

class RegistrationActivity : AppCompatActivity() {
    lateinit var userApi: UserApi
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        toLoginBtn.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "fadein-to-fadeout")

        }
        doRegistrationBtn.setOnClickListener {
            if (registerEmail.text.isEmpty() || !registerEmail.text.contains("@")) {
                registerEmail.error = "email required"
            }
            if (registerPassword.text.isEmpty()) {
                registerPassword.error = "password required"
            }
            configureRetrofit()
            doRegistration()

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

    private fun doRegistration() {
        configureRetrofit()
        val user = UserRequest(
            registerEmail.text.toString().trim(),
            registerPassword.text.toString().trim()
        )
        userApi.createUser(user)
            .enqueue(object : Callback<CreateUserResponse> {
                override fun onResponse(
                    call: Call<CreateUserResponse>,
                    response: Response<CreateUserResponse>
                ) {
                    Log.e("response", response.toString())
                    Log.e("response", "GOOD")
                }

                override fun onFailure(call: Call<CreateUserResponse>, t: Throwable) {
                    Log.e("failure", "failure", t)
                    Log.e("response", "OH NO")
                }

            })
    }
}