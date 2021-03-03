package com.example.androiddevchallenge.domain

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.time.hours

val Long.formatTime : String get() = SimpleDateFormat("hh:mm:ss").format(Date(this))

val Long.hours: Int get() = formatTime.split(":")[0].toInt()
val Long.minutes: Int get() = formatTime.split(":")[1].toInt()
val Long.seconds: Int get() = formatTime.split(":")[2].toInt()


fun Int.toNumber() : String {
    return if (this < 10) "0$this" else toString()
}