package com.example.meditation4u.RecyclerViewClass

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.meditation4u.R
import com.example.meditation4u.UserApi.QuotesList
import com.example.meditation4u.databinding.MenuItemBinding


class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MenuHolder>() {
    val menuList = ArrayList<QuotesList>()

    class MenuHolder(v: View) : RecyclerView.ViewHolder(v) {
        val binding = MenuItemBinding.bind(v)
        val context = v.context
        lateinit var btn: Button
        fun bind(menu: QuotesList) = with(binding) {
            menuHeader.text = menu.quotesTitle
            menuDescription.text = menu.quotesDescription
            Glide
                .with(context)
                .load(menu.quotesImage)
                .into(menuBackground)
            btn = menuBtn
            btn.setOnClickListener {
                Toast.makeText(context, menu.quotesDescription, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuHolder(view)
    }

    override fun onBindViewHolder(holder: MenuHolder, position: Int) {
        holder.bind(menuList[position])
        holder.itemView.setOnClickListener {
            Log.e("item", holder.toString())
        }
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    fun addItem(q: QuotesList) {
        menuList.add(q)
        notifyDataSetChanged()
    }
}
