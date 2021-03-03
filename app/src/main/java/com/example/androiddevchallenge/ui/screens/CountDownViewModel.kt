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

    val stateFlow: MutableStateFlow<CountDownViewState>  = MutableStateFlow(CountDownViewState())
    var  timerJob: Job? = null
    fun onTimerSet(timeMs: Long) {
        stateFlow.value = stateFlow.value.copy(timerTime = timeMs, isActive = true)
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            createTickFlow().collect {
                stateFlow.value = stateFlow.value.copy(timerTime = stateFlow.value.timerTime - 1000L)
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