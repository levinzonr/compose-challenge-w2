package com.example.androiddevchallenge.ui.screens.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.domain.hours
import com.example.androiddevchallenge.domain.minutes
import com.example.androiddevchallenge.domain.seconds
import com.example.androiddevchallenge.domain.toNumber


@Composable
fun CountDown(timeDiff: Long, progress: Float,  modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        TimerTime(timeDiff = timeDiff)
        CircularProgressIndicator(progress = progress)
    }
}

@Composable
fun TimerTime(timeDiff: Long) {
    Row(modifier = Modifier
        .padding(16.dp)) {
        TimeTextView(number = timeDiff.hours)
        Separator()
        TimeTextView(number = timeDiff.minutes)
        Separator()
        TimeTextView(number = timeDiff.seconds)
    }
}

@Composable
fun TimeTextView(number: Int) {
    Text(text = number.toNumber(), style = MaterialTheme.typography.h2)
}

@Composable
fun Separator() {
    Text(text = " : ", style = MaterialTheme.typography.h3)
}


@Preview
@Composable
fun CountDownPreview() {
    CountDown(timeDiff = 180000, 0.5f)
}
