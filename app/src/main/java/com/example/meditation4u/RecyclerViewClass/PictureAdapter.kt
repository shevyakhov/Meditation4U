package com.example.meditation4u.RecyclerViewClass

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meditation4u.R
import com.example.meditation4u.UserApi.PicList
import com.example.meditation4u.databinding.PictureItemBinding
import java.util.*
import kotlin.collections.ArrayList


class PictureAdapter : RecyclerView.Adapter<PictureAdapter.PicHolder>() {
    val picList = ArrayList<PicList>()

    class PicHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding = PictureItemBinding.bind(v)
        val context = v.context

        @SuppressLint("SetTextI18n")
        fun bind(item: PicList) = with(binding) {
            val currentTime: Date = Calendar.getInstance().time
            pictureDate.text = currentTime.hours.toString()+ ":"+ currentTime.minutes.toString()
            pictureSrc.setImageResource(item.image)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.picture_item, parent, false)
        return PicHolder(view)
    }

    override fun onBindViewHolder(holder: PicHolder, position: Int) {
        holder.bind(picList[position])
        if (position == itemCount-1) {
            holder.itemView.setOnClickListener {
                Log.e("main","MAIN")
            }

        }else{
            holder.itemView.setOnClickListener {
                Log.e("sub","sub")
            }
        }
    }

    override fun getItemCount(): Int {
        return picList.size
    }

    fun addItem(q: PicList) {
        picList.add(q)
        notifyDataSetChanged()
    }
}
