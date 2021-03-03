package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun SecondScreen() {
    Scaffold() {
        Box() {
            Text(text = "Second")
        }
    }
}