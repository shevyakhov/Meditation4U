package com.example.meditation4u.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.meditation4u.R
import com.example.meditation4u.constants.PICTURE
import kotlinx.android.synthetic.main.activity_picture_choice.*
import kotlinx.android.synthetic.main.picture_item.view.*

class PictureChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture_choice)

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



}