package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.androiddevchallenge.domain.hours
import com.example.androiddevchallenge.domain.minutes
import com.example.androiddevchallenge.domain.seconds
import com.example.androiddevchallenge.domain.toNumber


@Composable
fun CountDown(timeDiff: Long, modifier: Modifier = Modifier) {
    Row {
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
    CountDown(timeDiff = 180000)
}
