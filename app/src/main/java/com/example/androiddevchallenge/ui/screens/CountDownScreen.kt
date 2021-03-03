package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androiddevchallenge.ui.components.CountDown

@Composable
fun CountDownScreen(viewModel: CountDownViewModel) {
    Scaffold(backgroundColor = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        val state = viewModel.stateFlow.collectAsState()
        println("State: ${state.value}")
        if (!state.value.isActive) {
            Button(onClick = {viewModel.onTimerSet(System.currentTimeMillis() + 1800000)}) {
                Text(text = "Start")

            }
        } else {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CountDown(timeDiff = state.value.timerTime)
            }
        }

    }
}