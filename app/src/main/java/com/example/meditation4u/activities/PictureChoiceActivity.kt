package com.example.meditation4u.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation4u.R
import com.example.meditation4u.constants.PICTURE
import kotlinx.android.synthetic.main.activity_picture_choice.*
import maes.tech.intentanim.CustomIntent

class PictureChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_choice)

        setListeners()
    }

    private fun setListeners() {
        /* since gallery is immutable i did this */
        firstPic.setOnClickListener {
            val intent = Intent()
            intent.putExtra(PICTURE, R.drawable.gallery_1)
            setResult(RESULT_OK, intent)
            finish()
        }
        secondPic.setOnClickListener {
            val intent = Intent()
            intent.putExtra(PICTURE, R.drawable.gallery_2)
            setResult(RESULT_OK, intent)
            finish()
        }
        thirdPic.setOnClickListener {
            val intent = Intent()
            intent.putExtra(PICTURE, R.drawable.gallery_3)
            setResult(RESULT_OK, intent)
            finish()
        }
        forthPic.setOnClickListener {
            val intent = Intent()
            intent.putExtra(PICTURE, R.drawable.gallery_4)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "fadein-to-fadeout")
    }
}