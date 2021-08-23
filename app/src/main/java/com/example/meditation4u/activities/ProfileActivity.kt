package com.example.meditation4u.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.meditation4u.R
import com.example.meditation4u.RecyclerViewClass.FeelingsAdapter
import com.example.meditation4u.RecyclerViewClass.MenuAdapter
import com.example.meditation4u.UserApi.Feelings
import com.example.meditation4u.UserApi.LoginResponse
import com.example.meditation4u.UserApi.QuotesList
import com.example.meditation4u.UserApi.UserApi
import com.example.meditation4u.constants.*
import com.example.meditation4u.databinding.ActivityLoginBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_profile.*
import maes.tech.intentanim.CustomIntent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {
    private lateinit var userApi: UserApi
    private val compositeDisposable = CompositeDisposable()
    private lateinit var bindingFeelings: ActivityLoginBinding
    private lateinit var bindingMenu: ActivityLoginBinding
    private val feelingsAdapter = FeelingsAdapter()
    private val menuAdapter = MenuAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        bindingFeelings = ActivityLoginBinding.inflate(layoutInflater)
        bindingMenu = ActivityLoginBinding.inflate(layoutInflater)

        val user = initUser(intent)
        setProfile(user)

        @SuppressLint("HandlerLeak")
        val handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                val data = msg.obj as List<*>
                when (msg.what) {
                    0 -> for (feel in data.indices)
                        feelingsAdapter.addFeeling(data[feel] as Feelings)
                    1 -> for (quote in data.indices)
                        menuAdapter.addItem(data[quote] as QuotesList)
                }


            }
        }

        bindingInit()
        configureRetrofit()
        getFeelings(handler)
        getQuotes(handler)
        setListeners(user)

    }

    private fun setListeners(user: LoginResponse) {
        musicBtn.setOnClickListener {
            intent = Intent(this, MusicActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "fadein-to-fadeout")
        }
        profileBtn.setOnClickListener {
            intent = Intent(this, UserActivity::class.java)
            intent.putExtra(ID, user.id)
            intent.putExtra(EMAIL, user.email)
            intent.putExtra(NICKNAME, user.nickName)
            intent.putExtra(AVATAR, user.avatar)
            intent.putExtra(TOKEN, user.token)
            startActivity(intent)
            CustomIntent.customType(this, "fadein-to-fadeout")
        }
        profileHamburger.setOnClickListener {
            Toast.makeText(this, R.string.uselessBtn, Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindingInit() {
        bindingFeelings.apply {
            feelingsList.layoutManager =
                LinearLayoutManager(this@ProfileActivity, RecyclerView.HORIZONTAL, false)
            feelingsList.adapter = feelingsAdapter
        }
        bindingMenu.apply {
            menuList.layoutManager =
                LinearLayoutManager(this@ProfileActivity, RecyclerView.VERTICAL, false)
            menuList.adapter = menuAdapter

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

    private fun getFeelings(h: Handler) {
        compositeDisposable.add(userApi.getFeelings()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    val t = Thread {
                        val msg: Message = h.obtainMessage()
                        msg.obj = it.someData
                        msg.what = 0
                        h.sendMessage(msg)
                        Log.e("tag", it.someData.toString())

                    }
                    t.start()

                },
                {
                }
            ))
    }

    private fun getQuotes(h: Handler) {
        compositeDisposable.add(userApi.getQuotes()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    val t = Thread {
                        val msg: Message = h.obtainMessage()
                        msg.obj = it.someData
                        msg.what = 1
                        h.sendMessage(msg)
                        Log.e("tag", it.someData.toString())

                    }
                    t.start()

                },
                {
                }
            ))
    }

    private fun initUser(i: Intent): LoginResponse {
        val id = i.getStringExtra(ID)
        val email = i.getStringExtra(EMAIL)
        val nickName = i.getStringExtra(NICKNAME)
        val avatar = i.getStringExtra(AVATAR)
        val token = i.getStringExtra(TOKEN)
        return LoginResponse(id!!, email!!, nickName!!, avatar!!, token!!)
    }

    private fun setProfile(user: LoginResponse) {
        Glide
            .with(this)
            .load(user.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(profilePicture)
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