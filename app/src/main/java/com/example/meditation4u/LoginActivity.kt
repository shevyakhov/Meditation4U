package com.example.meditation4u

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation4u.UserApi.UserApi
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
        @SuppressLint("HandlerLeak")
        val h = object : Handler() {
           override fun handleMessage(msg: Message) {
               val data: String? = msg.obj as String?
               if (data != null) {
                   Log.e("Stuff", data)
                   signInText.text = data
               }else
                   Log.e("Stuff", "no data")
            }
        }
        toRegistration.setOnClickListener {
            intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            finish()
        }
        signInBtn.setOnClickListener {

            configureRetrofit()

            compositeDisposable.add(userApi.getPing()
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                            {
                                val t = Thread {

                                    val msg: Message = h.obtainMessage()
                                    msg.obj = it.someData
                                    h.sendMessage(msg)
                                    /*Log.e("Stuff", it.someData)*/
                                }
                                t.start()

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