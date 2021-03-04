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

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.components.FloatingButton
import com.example.androiddevchallenge.ui.screens.components.CountDown
import com.example.androiddevchallenge.ui.screens.components.TimeSelector

@Composable
fun CountDownScreen(viewModel: CountDownViewModel) {
    Scaffold(backgroundColor = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        val state = viewModel.stateFlow.collectAsState()
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            when (val state = state.value) {
                is CountDownViewState.Active -> {
                    CountDownActive(
                        state = state,
                        onStop = { viewModel.onStopButtonPressed() },
                        onPause = { viewModel.onPauseButtonClicked() }
                    )
                }
                is CountDownViewState.Default -> {
                    CountDownDefault(state = state, onClick = { viewModel.onTimerSet(it) })
                }

                is CountDownViewState.Paused -> {
                    CountDownPaused(
                        state = state,
                        onResume = { viewModel.onResumeButtonClicked() },
                        onStop = { viewModel.onStopButtonPressed() }
                    )
                }
            }
        }
    }
}

@Composable
fun CountDownActive(state: CountDownViewState, onStop: () -> Unit, onPause: () -> Unit) {
    CountDown(
        timeDiff = state.timeRemains,
        progress = state.progress,
        modifier = Modifier.padding(32.dp)
    )

    FloatingButton(
        onClick = onPause,
        icon = Icons.Rounded.Pause
    )

    Spacer(modifier = Modifier.size(16.dp))

    TextButton(
        onClick = onStop,
        modifier = Modifier.fillMaxWidth(0.8f),
    ) {
        Text(text = "Stop")
    }
}

@Composable
fun CountDownPaused(state: CountDownViewState, onResume: () -> Unit, onStop: () -> Unit) {
    CountDown(
        timeDiff = state.timeRemains,
        progress = state.progress,
        modifier = Modifier.padding(32.dp)
    )

    FloatingButton(
        onClick = onResume,
        icon = Icons.Rounded.PlayArrow
    )

    Spacer(modifier = Modifier.size(16.dp))

    TextButton(
        onClick = onStop,
        modifier = Modifier.fillMaxWidth(0.8f),
    ) {
        Text(text = "Stop")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CountDownDefault(state: CountDownViewState, onClick: (Long) -> Unit) {
    val states = remember(calculation = { mutableStateOf(0L) })
    TimeSelector(
        onTimeChanged = {
            states.value = it.calculateTime()
        }
    )
    Spacer(modifier = Modifier.size(32.dp))
    AnimatedVisibility(visible = states.value > 0) {
        Button(

            modifier = Modifier
                .fillMaxWidth(0.5f)
                .height(56.dp),
            onClick = { onClick.invoke(states.value) },
            enabled = states.value > 0
        ) {

            Text(text = "Start")
        }
    }
}
