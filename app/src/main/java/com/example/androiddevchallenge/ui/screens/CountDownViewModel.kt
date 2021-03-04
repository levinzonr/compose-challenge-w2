/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

class CountDownViewModel : ViewModel() {

    val stateFlow: MutableStateFlow<CountDownViewState> = MutableStateFlow(CountDownViewState.Default)
    var timerJob: Job? = null

    companion object {
        const val REFRESH_PERIOD = 10L
    }

    fun onTimerSet(timeMs: Long) {
        stateFlow.value = CountDownViewState.Active(timeMs, timeMs)
        timerJob?.cancel()
        timerJob = createTickAndSubscribe()
    }

    fun onPauseButtonClicked() {
        timerJob?.cancel()
        stateFlow.value = CountDownViewState.Paused(stateFlow.value.timeRemains, stateFlow.value.timerTime)
    }

    fun onStopButtonPressed() {
        timerJob?.cancel()
        stateFlow.value = CountDownViewState.Default
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
                stateFlow.value = CountDownViewState.Active(timeRemains = current - REFRESH_PERIOD, stateFlow.value.timerTime)
            }
        }
    }

    private fun createTickFlow(period: Long = REFRESH_PERIOD) = callbackFlow<Unit> {
        val timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    offer(Unit)
                }
            },
            0, period
        )
        awaitClose {
            timer.cancel()
        }
    }
}
