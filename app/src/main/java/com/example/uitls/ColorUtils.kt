package com.example.uitls

import com.example.notesapp.R
import kotlin.random.Random

fun randomColor(): Int {
    val list = ArrayList<Int>()
    list.add(R.color.Aqua)
    list.add(R.color.green)
    list.add(R.color.Blue)
    list.add(R.color.Fuchsia)
    list.add(R.color.Yellow)

    val seed = System.currentTimeMillis().toInt()
    val randomIndex = Random(seed).nextInt(list.size)
    return list[randomIndex]
}