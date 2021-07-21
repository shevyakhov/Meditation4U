package com.example.meditation4u

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.meditation4u.UserApi.UserApi
import com.google.gson.Gson
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    lateinit var userApi: UserApi
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        toRegistration.setOnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            /*intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP*/
            startActivity(intent)
            finish()
        }
        signInBtn.setOnClickListener {

            configureRetrofit()
            compositeDisposable.add(userApi.getPing()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            {

                                Log.e("Stuff", it.someData)
                               /* signInText.text = it.someData*/

                            },
                            {

                            }
                    ))


        }
    }

   private fun configureRetrofit(){
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
}