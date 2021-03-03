package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.androiddevchallenge.ui.screens.components.CountDown

@Composable
fun CountDownScreen(viewModel: CountDownViewModel) {
    Scaffold(backgroundColor = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        val state = viewModel.stateFlow.collectAsState()
        println("State: ${state.value}")
        when (val timer = state.value) {
            is CountDownViewState.Active -> CountDownActive(timer) { viewModel.onPauseButtonClicked()}
            is CountDownViewState.Default -> CountDownDefault(timer) { viewModel.onTimerSet(18000)}
            is CountDownViewState.Paused -> CountDownPaused(timer) { viewModel.onResumeButtonClicked() }
        }

    }
}

@Composable
fun CountDownActive(state: CountDownViewState, onClick: () -> Unit) {
    Column {
        CountDown(timeDiff = state.timeRemains, progress = state.progress)
        Button(onClick = { onClick.invoke() }) {
            Text(text = "Pause")
        }
    }
}

@Composable
fun CountDownPaused(state: CountDownViewState, onClick: () -> Unit) {
    Column {
        CountDown(timeDiff = state.timeRemains, progress = state.progress)
        Button(onClick = onClick) {
            Text(text = "Play")
        }
    }
}

@Composable
fun CountDownDefault(state: CountDownViewState, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Start")
    }
}