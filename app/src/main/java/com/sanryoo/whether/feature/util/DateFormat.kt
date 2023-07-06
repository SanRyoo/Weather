package com.sanryoo.whether.feature.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


//Date format in API
@SuppressLint("SimpleDateFormat")
val remoteDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

//To group date
@SuppressLint("SimpleDateFormat")
val myDateFormat = SimpleDateFormat("yyyy-MM-dd")

@SuppressLint("SimpleDateFormat")
val hourAndDayFormat = SimpleDateFormat("HH:mm EEEE")

@SuppressLint("SimpleDateFormat")
val hourFormat = SimpleDateFormat("HH:mm")

@SuppressLint("SimpleDateFormat")
val dayAndDateMonthFormat = SimpleDateFormat("EEEE,\nMMM dd")