package com.example.meditation4u.RecyclerViewClass

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation4u.R
import com.example.meditation4u.UserApi.PicList
import com.example.meditation4u.activities.PictureChoiceActivity
import com.example.meditation4u.databinding.PictureItemBinding
import java.util.*
import kotlin.collections.ArrayList


class PictureAdapter() : RecyclerView.Adapter<PictureAdapter.PicHolder>() {
    val picList = ArrayList<PicList>()
    var launcher: ActivityResultLauncher<Intent>? = null
    class PicHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding = PictureItemBinding.bind(v)
        val context = v.context

        @SuppressLint("SetTextI18n")
        fun bind(item: PicList) = with(binding) {

            if (item.image != R.drawable.plus) {
                pictureDate.text = item.date
                pictureSrc.scaleType = ImageView.ScaleType.FIT_XY
            }
            pictureSrc.setImageResource(item.image)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.picture_item, parent, false)
        return PicHolder(view)
    }

    override fun onBindViewHolder(holder: PicHolder, position: Int) {
        holder.bind(picList[position])

        if (position == itemCount - 1) {
            holder.itemView.setOnClickListener {
                val context =holder.itemView.context as Activity
                Log.e("main", "MAIN")
                val intent = Intent(context, PictureChoiceActivity::class.java)
                launcher?.launch(intent)
            }

        } else {
            holder.itemView.setOnClickListener {
                Log.e("sub", "sub")
            }
        }
    }

    override fun getItemCount(): Int {
        return picList.size
    }

    fun addItem(q: PicList) {
        picList.add(itemCount-1, q)
        notifyDataSetChanged()
    }
    fun addItemFirst(q: PicList) {
        picList.add(q)
        notifyDataSetChanged()
    }

    fun addLauncher(l: ActivityResultLauncher<Intent>?) {
        launcher = l
    }
}
