package com.example.meditation4u.activities

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.meditation4u.R
import com.example.meditation4u.classes.DoubleClickListener
import com.example.meditation4u.constants.ITEM_CODE
import com.example.meditation4u.constants.ITEM_DELETE
import com.example.meditation4u.constants.PICTURE
import com.example.meditation4u.constants.POSITION
import kotlinx.android.synthetic.main.activity_image.*
import maes.tech.intentanim.CustomIntent

@Suppress("DEPRECATION")
class ImageActivity : AppCompatActivity() {
    private var clickCounter: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        val id = intent?.getIntExtra(PICTURE, 0)
        val position = intent?.getIntExtra(POSITION, -1)

        setImage(id)

        setListeners(position)
    }

    private fun setListeners(position:Int?) {
        pickedImage.setOnClickListener(object : DoubleClickListener() {
            override fun onDoubleClick(v: View) {
                if (clickCounter % 2 == 0) {
                    increaseViewSize(pickedImage)
                } else
                    decreaseViewSize(pickedImage)
                clickCounter++
            }
        })
        closeImgBtn.setOnClickListener {
            finish()
        }
        deleteImgBtn.setOnClickListener {
            val result = Intent()
            result.putExtra(ITEM_CODE, ITEM_DELETE)
            result.putExtra(POSITION, position)
            setResult(RESULT_FIRST_USER, result)
            finish()
        }
    }

    private fun setImage(id: Int?) {
        if (id != null)
            pickedImage.setImageResource(id)
    }

    override fun finish() {
        super.finish()
        CustomIntent.customType(this, "fadein-to-fadeout")
    }

    private fun increaseViewSize(view: View) {
        val valueAnimatorHeight =
            ValueAnimator.ofInt(view.measuredHeight, view.measuredHeight + 400)
        valueAnimatorHeight.duration = 500L
        valueAnimatorHeight.addUpdateListener {
            val animatedValueHeight = valueAnimatorHeight.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValueHeight
            view.layoutParams = layoutParams
        }
        valueAnimatorHeight.start()
        val valueAnimatorWidth = ValueAnimator.ofInt(view.measuredWidth, view.measuredWidth + 400)
        valueAnimatorWidth.duration = 500L
        valueAnimatorWidth.addUpdateListener {
            val animatedValueWidth = valueAnimatorWidth.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.width = animatedValueWidth
            view.layoutParams = layoutParams
        }
        valueAnimatorWidth.start()
    }

    private fun decreaseViewSize(view: View) {
        val valueAnimatorHeight =
            ValueAnimator.ofInt(view.measuredHeight, view.measuredHeight - 400)
        valueAnimatorHeight.duration = 500L
        valueAnimatorHeight.addUpdateListener {
            val animatedValueHeight = valueAnimatorHeight.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValueHeight
            view.layoutParams = layoutParams
        }
        valueAnimatorHeight.start()
        val valueAnimatorWidth = ValueAnimator.ofInt(view.measuredWidth, view.measuredWidth - 400)
        valueAnimatorWidth.duration = 500L
        valueAnimatorWidth.addUpdateListener {
            val animatedValueWidth = valueAnimatorWidth.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.width = animatedValueWidth
            view.layoutParams = layoutParams
        }
        valueAnimatorWidth.start()
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
