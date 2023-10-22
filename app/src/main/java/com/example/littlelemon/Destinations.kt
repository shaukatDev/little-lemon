package com.example.littlelemon


interface Destinations {
    val route: String
}

object Home : Destinations {
    override val route = "Home"
}

object OnBoarding : Destinations {
    override val route = "OnBoarding"
}

object Profile : Destinations {
    override val route = "Profile"
}
