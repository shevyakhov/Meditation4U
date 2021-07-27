package com.example.meditation4u.RecyclerViewClass

import com.example.meditation4u.R
import com.example.meditation4u.UserApi.MenuList

private val first = MenuList("Цитаты", "Здесь будут подбадривающие цитаты", R.drawable.yoga_profile)

private val second = MenuList("Тестовый блок", "Описание", R.drawable.heart_profile)

val menuArray = arrayListOf<MenuList>(first , second)