package com.example.androiddevchallenge.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class CountDownViewModel : ViewModel() {

    val stateFlow: MutableStateFlow<CountDownViewState>  = MutableStateFlow(CountDownViewState.Default)
    var  timerJob: Job? = null


    fun onTimerSet(timeMs: Long) {
        stateFlow.value = CountDownViewState.Active(timeMs, timeMs)
        timerJob?.cancel()
        timerJob = createTickAndSubscribe()
    }

    fun onPauseButtonClicked() {
        timerJob?.cancel()
        stateFlow.value = CountDownViewState.Paused(stateFlow.value.timeRemains, stateFlow.value.timerTime)
    }

    fun onResumeButtonClicked() {
        timerJob = createTickAndSubscribe()
        stateFlow.value = CountDownViewState.Active(stateFlow.value.timeRemains, stateFlow.value.timerTime)

    }


    private fun createTickAndSubscribe() = viewModelScope.launch {
        createTickFlow().collect {
            val remains = stateFlow.value.timeRemains
            if (remains <= 0) {
                stateFlow.value = CountDownViewState.Default
            } else {
                val current = stateFlow.value.timeRemains
                stateFlow.value = CountDownViewState.Active(timeRemains = current - 1000L, stateFlow.value.timerTime)
            }
        }
    }

    private fun createTickFlow(period: Long = 1000L) = callbackFlow<Unit>{
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                offer(Unit)
            }
        }, 0, period)
        awaitClose {
            timer.cancel()
        }
    }
}