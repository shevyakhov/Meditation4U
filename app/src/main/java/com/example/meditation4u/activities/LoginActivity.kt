package com.example.meditation4u.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation4u.R
import com.example.meditation4u.UserApi.LoginResponse
import com.example.meditation4u.UserApi.UserApi
import com.example.meditation4u.UserApi.UserRequest
import com.google.android.gms.common.api.Api
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_registration.*
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
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        configureRetrofit()
        signInBtn.setOnClickListener {

           val client = onLogin()
            if (client !== null ) {
                intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }else
                Log.e("check","null client")
        }
        toRegistration.setOnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)

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

    private fun onLogin(): LoginResponse? {
        val user = UserRequest(
            signInEmail.text.toString().trim(),
            signInPassword.text.toString().trim()
        )
        var userLogged: LoginResponse? = null
        userApi.getLogin(user)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Log.e("response", response.toString())
                    userLogged = response.body()
                    /*TODO NULL EXCEPTION*/
                    Log.e("!!!!!!",userLogged.toString())
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("failure", "failure", t)
                }

            })
        return userLogged
    }
}