package com.example.androiddevchallenge.ui.screens


sealed class CountDownViewState(val timeRemains: Long, val timerTime: Long) {
    class Active(timeRemains: Long, timerTime: Long)  : CountDownViewState(timeRemains, timerTime)
    class Paused(timeRemains: Long, timerTime: Long) : CountDownViewState(timeRemains, timerTime)
    object Default: CountDownViewState(-1, -1)
    val progress: Float = timeRemains  / timerTime.toFloat()

}
