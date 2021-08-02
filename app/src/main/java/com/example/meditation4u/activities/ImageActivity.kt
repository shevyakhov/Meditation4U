package com.example.meditation4u.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.meditation4u.R
import com.example.meditation4u.constants.ITEM_CODE
import com.example.meditation4u.constants.ITEM_DELETE
import com.example.meditation4u.constants.PICTURE
import com.example.meditation4u.constants.POSITION
import kotlinx.android.synthetic.main.activity_image.*
import maes.tech.intentanim.CustomIntent

class ImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        val id = intent?.getIntExtra(PICTURE,0)
        val position = intent?.getIntExtra(POSITION,-1)
        setImage(id)

        deleteImgBtn.setOnClickListener {
            val result = Intent()
            result.putExtra(ITEM_CODE, ITEM_DELETE)
            result.putExtra(POSITION, position)
            setResult(RESULT_FIRST_USER, result)
            finish()
        }

        closeImgBtn.setOnClickListener {
            finish()
        }

    }
    private fun setImage(id:Int?){
        if (id != null)
        pickedImage.setImageResource(id)
    }
    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "fadein-to-fadeout")
    }
}