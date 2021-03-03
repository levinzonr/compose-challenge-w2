package com.example.androiddevchallenge.routing

import androidx.navigation.NavHostController
import androidx.navigation.compose.navigate

object Destinations {
    const val First = "tasks"
    const val Second = "goals"
}

class Router(private val navHostController: NavHostController) {
    fun navigateBack() { navHostController.popBackStack() }
    fun showSecond() { navHostController.navigate(Destinations.Second) }

}