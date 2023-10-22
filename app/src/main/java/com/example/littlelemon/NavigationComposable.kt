package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController, appDatabase: AppDatabase) {
    NavHost(
        navController = navController,
        startDestination = OnBoarding.route
    ) {
        composable(OnBoarding.route) {
            OnBoarding(navController)
        }
        composable(Home.route) {
            Home(navController,appDatabase)
        }
        composable(Profile.route) {
            Profile(navController)
        }
    }
}
