package com.example.meditation4u.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation4u.R
import com.example.meditation4u.RecyclerViewClass.FeelingsAdapter
import com.example.meditation4u.UserApi.Feelings
import com.example.meditation4u.UserApi.UserApi
import com.example.meditation4u.databinding.ActivityLoginBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ProfileActivity : AppCompatActivity() {
    lateinit var userApi: UserApi
    private val compositeDisposable = CompositeDisposable()
    lateinit var binding: ActivityLoginBinding
    private val adapter = FeelingsAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_profile)

        @SuppressLint("HandlerLeak")
        val h = object : Handler() {
            override fun handleMessage(msg: Message) {
                val data = msg.obj as List<Feelings>
                for (feel in data.indices)
                    adapter.addFeeling(data[feel])
            }
        }

        bindingInit()

        configureRetrofit()
        compositeDisposable.add(userApi.getFeelings()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    val t = Thread {
                        val msg: Message = h.obtainMessage()
                        msg.obj = it.someData
                        h.sendMessage(msg)
                        Log.e("tag", it.someData.toString())

                    }
                    t.start()

                },
                {
                }
            ))

        profileHamburger.setOnClickListener{
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun bindingInit() {
        binding.apply {
            feelingsList.layoutManager =
                LinearLayoutManager(this@ProfileActivity, RecyclerView.HORIZONTAL, false)
            feelingsList.adapter = adapter


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

}