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
import com.bumptech.glide.Glide
import com.example.meditation4u.R
import com.example.meditation4u.RecyclerViewClass.FeelingsAdapter
import com.example.meditation4u.RecyclerViewClass.MenuAdapter
import com.example.meditation4u.RecyclerViewClass.menuArray
import com.example.meditation4u.UserApi.Feelings
import com.example.meditation4u.UserApi.LoginResponse
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
    lateinit var bindingFeelings: ActivityLoginBinding
    lateinit var bindingMenu: ActivityLoginBinding
    private val feelingsAdapter = FeelingsAdapter()
    private val menuAdapter = MenuAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingFeelings = ActivityLoginBinding.inflate(layoutInflater)
        bindingMenu = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_profile)

        val user = initUser(intent)
        setProfile(user)
        @SuppressLint("HandlerLeak")
        val h = object : Handler() {
            override fun handleMessage(msg: Message) {
                val data = msg.obj as List<Feelings>
                for (feel in data.indices)
                    feelingsAdapter.addFeeling(data[feel])

            }
        }

        setMenuRecycler()

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

        profileHamburger.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
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

            Log.e("1", "done")
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

    private fun initUser(i: Intent): LoginResponse {
        val id = i.getStringExtra("id")
        val email = i.getStringExtra("email")
        val nickName = i.getStringExtra("nickName")
        val avatar = i.getStringExtra("avatar")
        val token = i.getStringExtra("token")
        return LoginResponse(id!!, email!!, nickName!!, avatar!!, token!!)
    }

    private fun setProfile(user: LoginResponse) {
        Glide
            .with(this)
            .load(user.avatar)
            .into(profilePicture)
    }

    private fun setMenuRecycler() {
        for (menuItem in menuArray.indices)
            menuAdapter.addItem(menuArray[menuItem])

    }
}