@file:Suppress("DEPRECATION")

package com.example.meditation4u.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.meditation4u.R
import com.example.meditation4u.RecyclerViewClass.PictureAdapter
import com.example.meditation4u.UserApi.LoginResponse
import com.example.meditation4u.UserApi.PicList
import com.example.meditation4u.constants.*
import com.example.meditation4u.databinding.ActivityUserBinding
import kotlinx.android.synthetic.main.activity_user.*
import maes.tech.intentanim.CustomIntent
import java.text.SimpleDateFormat
import java.util.*

class UserActivity : AppCompatActivity() {
    private lateinit var picBinding: ActivityUserBinding
    private var launcher: ActivityResultLauncher<Intent>? = null
    private val adapter = PictureAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = initUser(intent)
        picBinding = ActivityUserBinding.inflate(layoutInflater)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        setContentView(picBinding.root)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    val id = result.data?.getIntExtra(PICTURE, 0)
                    val currentTime =
                        SimpleDateFormat("HH:mm", Locale.US).format(Calendar.getInstance().time)
                    if (id != null) {

                        adapter.addItem(PicList(id, currentTime))
                    }
                }
                if (result.resultCode == RESULT_FIRST_USER) {
                    val id = result.data?.getIntExtra(ITEM_CODE, ITEM_STAY)
                    if (id == ITEM_DELETE) {
                        val position = result.data?.getIntExtra(POSITION, ITEM_STAY)
                        if (position != null)
                            adapter.deleteItem(position)
                    }
                }
            }
        adapter.addLauncher(launcher)

        bindingInit()

        setProfile(user)
        userExit.setOnClickListener {
            saveData(LoginResponse(EMPTY, EMPTY, EMPTY, EMPTY, EMPTY))
            intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            CustomIntent.customType(this, "fadein-to-fadeout")
            finish()
        }
        userHome.setOnClickListener {
            finish()
        }
    }

    private fun bindingInit() {
        picBinding.apply {
            recyclerPictures.layoutManager =
                GridLayoutManager(this@UserActivity, 2)
            recyclerPictures.adapter = adapter
        }
        adapter.addItemFirst(PicList(R.drawable.plus, EMPTY))
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
            .into(userPic)
        userName.text = user.nickName
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

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "fadein-to-fadeout")
    }

}