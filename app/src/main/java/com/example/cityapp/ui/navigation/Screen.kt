package com.example.cityapp.ui.navigation

sealed class Screen(val route: String) {
    object Home: Screen("home")
    object Profile: Screen("profile")
    object DetailCity: Screen("home/{cityId}") {
        fun createRoute(cityId: Long) = "home/$cityId"
    }
}
