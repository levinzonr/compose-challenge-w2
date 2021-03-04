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
package com.example.androiddevchallenge.ui.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.domain.hours
import com.example.androiddevchallenge.domain.minutes
import com.example.androiddevchallenge.domain.seconds
import com.example.androiddevchallenge.domain.toNumber

@Composable
fun CountDown(timeDiff: Long, progress: Float, modifier: Modifier = Modifier) {
    ConstraintLayout(modifier = modifier) {
        val (cricle, timer) = createRefs()
        val cirlceConstraint = Modifier.constrainAs(cricle) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
            start.linkTo(parent.start)
        }

        val timerConstraint = Modifier.constrainAs(timer) {
            start.linkTo(cricle.start)
            end.linkTo(cricle.end)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }

        CircularProgressIndicator(
            progress = progress,
            strokeWidth = 12.dp,
            modifier = cirlceConstraint
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        TimerTime(timeDiff = timeDiff, modifier = timerConstraint)
    }
}

@Composable
fun TimerTime(timeDiff: Long, modifier: Modifier) {
    Row(
        modifier = modifier
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TimeTextView(number = timeDiff.hours)
        Separator()
        TimeTextView(number = timeDiff.minutes)
        Separator()
        TimeTextView(number = timeDiff.seconds)
    }
}

@Composable
fun TimeTextView(number: Int, color: Color = MaterialTheme.colors.primary) {
    Text(text = number.toNumber(), style = MaterialTheme.typography.h3, color = color)
}

@Composable
fun Separator(modifier: Modifier = Modifier) {
    Text(text = " : ", style = MaterialTheme.typography.h4, modifier = modifier)
}

@Preview
@Composable
fun CountDownPreview() {
    CountDown(timeDiff = 180000, 0.5f)
}
