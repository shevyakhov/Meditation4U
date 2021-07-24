package com.example.meditation4u.RecyclerViewClass

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meditation4u.R
import com.example.meditation4u.UserApi.Feelings
import com.example.meditation4u.databinding.FeelingItemBinding

class FeelingsAdapter : RecyclerView.Adapter<FeelingsAdapter.FeelingsHolder>() {
    val feelingList = ArrayList<Feelings>()

    class FeelingsHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding = FeelingItemBinding.bind(v)
        val context = v.context
        fun bind(feeling: Feelings) = with(binding) {
            feelName.text = feeling.feelingsTitle
            Glide
                .with(context)
                .load(feeling.feelingsImage)
                .into(feelBackground)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeelingsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.feeling_item, parent, false)
        return FeelingsHolder(view)
    }

    override fun onBindViewHolder(holder: FeelingsHolder, position: Int) {
        holder.bind(feelingList[position])
    }

    override fun getItemCount(): Int {
        return feelingList.size
    }

    fun addFeeling(f: Feelings) {
        feelingList.add(f)
        notifyDataSetChanged()
    }
}
