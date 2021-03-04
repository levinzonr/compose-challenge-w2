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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class TimeSelectorState(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
) {
    fun calculateTime(): Long {
        return seconds * 1000L + minutes * 1000L * 60L + hours * 1000L * 60 * 60
    }
}

@Composable
fun TimeSelector(modifier: Modifier = Modifier, onTimeChanged: (TimeSelectorState) -> Unit) {
    var state by remember { mutableStateOf(TimeSelectorState()) }
    println("State: $state")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {

        TimeSectorView(limit = 100, label = "HH") {
            state = state.copy(hours = it)
            onTimeChanged.invoke(state)
        }
        Separator(modifier = Modifier.padding(bottom = 12.dp))
        TimeSectorView(limit = 60, label = "MM") {
            state = state.copy(minutes = it)
            onTimeChanged.invoke(state)
        }

        Separator(modifier = Modifier.padding(bottom = 12.dp))
        TimeSectorView(limit = 60, label = "SS") {
            println("Seconds: $it")
            state = state.copy(seconds = it)
            onTimeChanged.invoke(state)
        }
    }
}

class SnapFllig(val state: LazyListState) : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        val current = state.firstVisibleItemScrollOffset
        val first = state.layoutInfo.visibleItemsInfo.getOrNull(0)?.offset ?: 0
        val second = state.layoutInfo.visibleItemsInfo.getOrNull(1)?.offset ?: 0

        scrollBy(second.toFloat())
        return 0f
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimeSectorView(label: String, limit: Int = 60, onNumberSelected: (Int) -> Unit) {
    val state = rememberLazyListState()
    onNumberSelected.invoke(state.firstVisibleItemIndex)
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, color = MaterialTheme.colors.secondary)
        LazyColumn(state = state, horizontalAlignment = Alignment.CenterHorizontally) {
            items(limit) { index ->
                val selected = state.firstVisibleItemIndex == index
                val color = if (selected) MaterialTheme.colors.primary else Color.Gray
                TimeTextView(number = index, color = color)
            }
        }
    }
}
@Preview
@Composable
fun TimeSelectorPreview() {
    TimeSelector(
        onTimeChanged = {
            println("Time: $it")
        }
    )
}
