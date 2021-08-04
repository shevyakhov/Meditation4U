@file:Suppress("DEPRECATION")

package com.example.meditation4u.activities

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.meditation4u.R
import kotlinx.android.synthetic.main.activity_music.*
import maes.tech.intentanim.CustomIntent

class MusicActivity : AppCompatActivity() {

    var player: MediaPlayer? = null
    var song = mutableListOf<Int>(R.raw.relax)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        useSound(song[0])

    }

    private fun useSound(id: Int) {
        Toast.makeText(this, R.string.relax, Toast.LENGTH_SHORT).show()
        musicPlay.setOnClickListener {
            createPlayer(id)
            initSeekBar()
        }
        musicPause.setOnClickListener {
            pausePlayer()
        }

        musicStop.setOnClickListener {
            stopPlayer()
        }
        musicClose.setOnClickListener {
            stopPlayer()
            finish()
        }

        musicSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && player !== null) player!!.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                Log.e("tag", "touched")

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun initSeekBar() {
        musicSeekBar.max = player!!.duration
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    musicSeekBar.progress = player!!.currentPosition
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    musicSeekBar.progress = 0
                }
            }

        }, 0)

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

    private fun stopPlayer() {
        if (player !== null) {
            player?.stop()
            player?.reset()
            player?.release()
            player = null
        }
    }

    private fun createPlayer(id: Int) {
        if (player == null) {
            player = MediaPlayer.create(this, id)
        }
        player?.start()
    }

    private fun pausePlayer() {
        if (player !== null)
            player?.pause()

    }
}