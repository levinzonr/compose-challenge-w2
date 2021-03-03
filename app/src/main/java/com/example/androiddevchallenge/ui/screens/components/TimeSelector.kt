package com.example.androiddevchallenge.ui.screens.components
// for a 'val' variable
import androidx.compose.runtime.getValue

// for a `var` variable also add
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.domain.toNumber


data class TimeSelectorState(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
) {
    fun calculateTime() : Long {
        return  seconds * 1000L + minutes * 1000L * 60L + hours * 1000L * 60 * 60
    }
}

@Composable
fun TimeSelector(modifier: Modifier = Modifier, onTimeChanged: (TimeSelectorState) -> Unit) {
    var state by remember { mutableStateOf(TimeSelectorState()) }
    println("State: ${state}")
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {

        TimeSectorView() {
            state = state.copy(hours = it)
            onTimeChanged.invoke(state)
        }
        Separator()
        TimeSectorView() {
            state = state.copy(minutes = it)
            onTimeChanged.invoke(state)
        }

        Separator()
        TimeSectorView() {
            println("Seconds: $it")
            state = state.copy(seconds = it)
            onTimeChanged.invoke(state)
        }

    }
}



@Composable
fun TimeSectorView(limit: Int = 60, onNumberSelected: (Int) -> Unit) {
    val state = rememberLazyListState()
    onNumberSelected.invoke(state.firstVisibleItemIndex)
    LazyColumn(state = state) {
        items(limit) { index ->
            TimeTextView(number = index)
        }
    }
}
@Preview
@Composable
fun TimeSelectorPreview() {
    TimeSelector(onTimeChanged = {
        println("Time: $it")
    })
}