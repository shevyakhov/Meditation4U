package com.example.meditation4u.RecyclerViewClass

import com.example.meditation4u.R
import com.example.meditation4u.UserApi.MenuList

private val first = MenuList("Цитаты", "Здесь будут подбадривающие цитаты", R.drawable.yoga_profile)

private val second = MenuList("Тестовый блок", "Описание", R.drawable.heart_profile)

private val third = MenuList("Дерево", "Тут вы увидите деревья", R.drawable.common_google_signin_btn_icon_dark)

val menuArray = arrayListOf<MenuList>(first , second, third)